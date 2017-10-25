package com.wit.iris.elastic

import com.wit.iris.schemas.Schema
import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.json.JsonOutput

@Transactional
class ElasticService {

    final String endpoint = "https://search-iris-ibwkuxcv4b2unly3c7d2d77v2a.eu-west-1.es.amazonaws.com"

    RestBuilder rest = new RestBuilder()
    RestResponse resp

    /**
     * Creates an Elasticsearch index
     * @param schema - the schema to create the index for
     */
    void createIndex(Schema schema){
        //create index and mapping at the same time in one call
        resp = rest.put("$endpoint/$schema.esIndex"){
            contentType "application/json"
            json createMapping(schema)
        }
        println("Status code : $resp.statusCodeValue")
        println("Json response: ${resp.json.toString()}")
        //TODO see if the status is ok from elasticsearch, or else throw an exception
    }

    /**
     * Creates a mapping for schema
     * @return json formatted string representing the mapping for the schema
     */
    String createMapping(Schema schema){
        Map mapping = ["mappings" : ["schema" : ["properties" : [:]]]]
        Map properties = [:]
        schema.schemaFields.each{
            //strings are now type 'text' in Elasticsearch
            String fieldType = it.fieldType == "String" ? "keyword" :  it.fieldType
            properties += [(it.name) : ["type": fieldType]]
        }
        mapping.mappings.schema.properties = properties
        return JsonOutput.toJson(mapping)
    }

    String updateMapping(String esIndex, List legacyFields, List updatedFields){
        //compare the legacy and new lists
        //grab the new items
        //update the mapping with only the new fields
        
    }

    /**
     * Deletes a schemas elasticsearch index
     * @param schema, the schemas index to delete
     */
    void deleteIndex(String esIndex){
        resp = rest.delete("$endpoint/$esIndex")
        println("Status code : $resp.statusCodeValue")
        println("Json response: ${resp.json.toString()}")
    }

    /**
     * Creates a suitable index name from a Schema name
     * @param schemaName
     * @return The elasticsearch index string
     */
    String getIndexFromName(String schemaName){
        return schemaName.toLowerCase().replaceAll(" ", "_")
    }
}
