package com.wit.iris.elastic

import com.wit.iris.com.wit.tests.domains.utils.DomainUtils
import com.wit.iris.schemas.Schema
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ElasticServiceSpec extends Specification {

    @Autowired
    ElasticService elasticService

    String esEndpoint
    String esIndex

    Schema schema

    def setup() {
        esEndpoint = "https://search-iris-ibwkuxcv4b2unly3c7d2d77v2a.eu-west-1.es.amazonaws.com"
        esIndex = "shakespeare"
        schema = DomainUtils.getSchemaWithSingleSchemaField()
    }

    def cleanup() {
        Map resp = elasticService.deleteIndex(schema.esIndex)
        assert resp.statusCodeValue == 200
    }

    void "test creating index" (){
        when: "I create an elasticsearch index"
        Map resp = elasticService.createIndex(schema)

        then: "the response is 200"
        assert resp.statusCodeValue == 200
    }

    void "test getEsIndexFromName"(){
        setup:
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
    }
}
