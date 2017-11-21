package com.wit.iris.elastic

import com.wit.iris.schemas.Schema
import com.wit.iris.users.User
import grails.plugins.rest.client.RestResponse
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class ElasticServiceSpec extends Specification {

    ElasticService elasticService

    ElasticEndpoint elasticEndpoint
    String shakespeareIndex

    User user
    Schema schema
    RestResponse resp

    def setupData(){
        elasticEndpoint = new ElasticEndpoint(name: "aws", url: "https://search-iris-ibwkuxcv4b2unly3c7d2d77v2a.eu-west-1.es.amazonaws.com", active: true)
        elasticEndpoint.save(flush: true)
        shakespeareIndex = "shakespeare"
        user = new User(username: "dean", password: "password")
        schema = new Schema(name: "test", esIndex: "test", refreshInterval: 1000, user: user)
        user.addToSchemas(schema).save(flush: true)

        assert ElasticEndpoint.count() == 1
        assert Schema.count() == 1
        assert User.count() == 1
    }

    def setup() {

    }

    def cleanup() {

    }

    void cleanElasticsearch(){
        if(elasticService.indexExists(schema.esIndex)){
            resp = elasticService.deleteIndex(schema.esIndex)
            assert resp.statusCodeValue == 200
        }
    }

    void "test aggregation execution"(){
        setup:
        setupData()

        when: "I execute an aggregation"
        Aggregation agg = new Aggregation(esIndex: shakespeareIndex, json: "{\n" +
                "\t\"aggs\":{\n" +
                "\t\t\"agg1\":{\n" +
                "\t\t\t\t\"terms\":{\"field\": \"play_name\"},\n" +
                "\t\t\t\t\"aggs\":{\n" +
                "\t\t\t\t\t\"agg2\":{\n" +
                "\t\t\t\t\t\t\"sum\":{\"field\" : \"speech_number\"}\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t}\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"size\": 0\n" +
                "}")

        resp = elasticService.executeAggregation(agg)

        then: "the status code is 200"
        assert resp.statusCodeValue == 200
    }

    void "test check if index exists"(){
        setup:
        setupData()

        when: "I create a new index"
        resp = elasticService.createIndex(schema)
        assert resp.statusCodeValue == 200

        then: "The index exists in elasticsearch"
        assert elasticService.indexExists(schema.esIndex)
        cleanElasticsearch()
    }

    void "test creating elasticsearch index" (){
        setup:
        setupData()

        when: "I create an index"
        resp = elasticService.createIndex(schema)

        then: "the response code is 200"
        assert resp.statusCodeValue == 200
        cleanElasticsearch()
    }

    void "test getEsIndexFromName"(){
        setup:
        setupData()
        String schemaName = "sql"

        when: "I get the es index "
        String esIndex = elasticService.getIndexFromName(schemaName)

        then: "It will remain the same"
        esIndex == schemaName

        when: "I change the schema name to be all caps"
        schemaName = "SQL"

        and: "I get the es index"
        esIndex = elasticService.getIndexFromName(schemaName)

        then: "it will be lowercase"
        esIndex == "sql"

        when: "I change the name to have spaces in it"
        schemaName = "sql monitor"

        and: "I get the name"
        esIndex = elasticService.getIndexFromName(schemaName)

        then: "the spaces get replaced by underscores"
        esIndex == "sql_monitor"

        when: "I do a mixture of upper, lower and spaces"
        schemaName = "SqL MoNiTor"

        and: "I get the index name"
        esIndex = elasticService.getIndexFromName(schemaName)

        then: "The name will be all lower case with underscores instead of spaces"
        esIndex == "sql_monitor"
        cleanElasticsearch()
    }

}
