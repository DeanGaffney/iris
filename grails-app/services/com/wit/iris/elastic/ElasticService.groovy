package com.wit.iris.elastic

import com.wit.iris.schemas.Schema
import com.wit.iris.schemas.SchemaField
import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput

@Transactional
class ElasticService {

    def restService

    final String ES_INDEX_TYPE = "schema"

    /**
     * Creates an Elasticsearch index
     * @param schema - the schema to create the index for
     */
    void createIndex(Schema schema){
        int status = restService.put("${getElasticEndpointUrl()}/$schema.esIndex", createMapping(schema))
        if(status != 200){
            //TODO see if the status is ok from elasticsearch, or else throw an exception
        }
    }

    /**
     * Creates a mapping for schema
     * @return json formatted string representing the mapping for the schema
     */
    String createMapping(Schema schema){
        Map mapping = ["mappings" : ["schema" : ["properties" : [:]]]]
        Map properties = [:]
        schema.schemaFields.each{
            properties += [(it.name) : ["type": convertDataType(it.fieldType)]]
        }
        mapping.mappings.schema.properties = properties
        return JsonOutput.toJson(mapping)
    }

    /**
     * Updates an Elasticsearch mapping with new fields
     * @param esIndex - the name of the index to update the mapping for
     * @param legacyFields - the previous Schema versions SchemaFields
     * @param updatedFields - the updated Schema versions SchemaFields
     */
    void updateMapping(String esIndex, List<SchemaField> legacyFields, List<SchemaField> updatedFields){
        List<SchemaField> difference = updatedFields - legacyFields     // the remainder will be the new schema fields added by the user
        if(!difference.isEmpty()){
            Map mapping = ["properties" : [:]]
            difference.each{
                mapping.properties += [(it.name) : ["type" : convertDataType(it.fieldType)]]
            }
            int status = restService.put("${getElasticEndpointUrl()}/$esIndex/_mapping/$ES_INDEX_TYPE", mapping)
            if(status != 200){
                //TODO see if the status is ok from elasticsearch, or else throw an exception
            }
        }
    }

    void insert(String esIndex, Map data){
       int status = restService.put("${getElasticEndpointUrl()}/$esIndex}", data)
        if(status != 200){
            //TODO throw exception
        }
    }

    /**
     * Converts data type inputted by user to the correct elastic search data type
     * if the field type is a String, it is replaced with keyword due to Elasticsearch needing keyword for aggregations
     * @param fieldType - the fieldType selected by the user
     * @return The fieldType to be used for the Elasticsearch mapping
     */
    String convertDataType(String fieldType){
        return fieldType == "String" ? "keyword" : fieldType
    }

    /**
     * Deletes a schemas elasticsearch index
     * @param schema, the schemas index to delete
     */
    void deleteIndex(String esIndex){
        int status = restService.delete("${getElasticEndpointUrl()}/$esIndex")
        if(status != 200){
            //TODO handle error
        }
    }

    /**
     * Creates a suitable index name from a Schema name
     * @param schemaName
     * @return The elasticsearch index string
     */
    String getIndexFromName(String schemaName){
        return schemaName.toLowerCase().replaceAll(" ", "_")
    }

    /**
     * Gets the Active Elasticsearch url endpoint
     * @return Elasticsearch endpoint url
     */
    String getElasticEndpointUrl(){
        String url = ElasticEndpoint.findByActive(true).url
        //remove a trailing slash if present
        return (url.endsWith("/")) ? url.substring(0, url.length() - 1) : url
    }
}
