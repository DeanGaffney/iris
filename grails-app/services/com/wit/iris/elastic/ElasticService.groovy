package com.wit.iris.elastic

import com.wit.iris.schemas.Schema
import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse

@Transactional
class ElasticService {

    final String endpoint = "https://search-iris-ibwkuxcv4b2unly3c7d2d77v2a.eu-west-1.es.amazonaws.com"

    RestBuilder rest = new RestBuilder()
    RestResponse resp

    /**
     * Creates an Elasticsearch index
     * @param schema
     */
    void createIndex(Schema schema){
        //create index and mapping at the same time in one call
        resp = rest.put("$endpoint/$schema.esIndex"){
            contentType "application/json"
            json createMapping()
        }

        //TODO see if the status is ok from elasticsearch, or else throw an exception
    }

    /**
     * Creates a mapping for schema, json format is specified below
     * PUT indexName
     {
        "mappings": {
            "schema": {
                "properties": {
                    "name": {
                        "type": "text"
                    },
                    "fieldType":{
                        "type" : "text"
                    }
                }
            }
        }
     }
     */
    String createMapping(){
        return "{\n" +
                "        \"mappings\": {\n" +
                "            \"schema\": {\n" +
                "                \"properties\": {\n" +
                "                    \"name\": {\n" +
                "                        \"type\": \"text\"\n" +
                "                    },\n" +
                "                    \"fieldType\":{\n" +
                "                        \"type\" : \"text\"\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "     }"
    }

    /**
     * Deletes a schemas elasticsearch index
     * @param schema, the schemas index to delete
     */
    void deleteIndex(Schema schema){
        resp = rest.delete("$endpoint/$schema.esIndex")
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
