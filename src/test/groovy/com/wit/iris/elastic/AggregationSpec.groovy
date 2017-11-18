package com.wit.iris.elastic

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class AggregationSpec extends Specification implements DomainUnitTest<Aggregation> {

    Aggregation aggregation

    def setupData(){
        aggregation = new Aggregation(esIndex: "some/index/es", json: "{json}", levels: 1)
        aggregation.save(flush: true)

        assert Aggregation.count() == 1
    }

    def setup() {
    }

    def cleanup() {
    }

    void "test aggregation esIndex constraints"(){
        setup:
        setupData()

        when:"I change the esIndex to be null"
        aggregation.esIndex = null

        then: "it is not valid"
        !aggregation.validate()

        when: "I change the esIndex to be blank"
        aggregation.esIndex = ""

        then: "it is not valid"
        !aggregation.validate()
    }

    void "test aggregation json constraints"(){
        setup:
        setupData()

        when: "I change the json to be null"
        aggregation.json = null

        then: "it is not valid"
        !aggregation.validate()

        when: "I change json to be blank"
        aggregation.json = ""

        then: "it is not valid"
        !aggregation.validate()
    }


}
