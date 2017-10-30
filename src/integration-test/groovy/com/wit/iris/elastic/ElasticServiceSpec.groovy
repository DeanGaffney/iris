package com.wit.iris.elastic

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class ElasticServiceSpec extends Specification {

    ElasticService elasticService

    def setup() {
    }

    def cleanup() {
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
