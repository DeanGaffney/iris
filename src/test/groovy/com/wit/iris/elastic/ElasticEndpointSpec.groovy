package com.wit.iris.elastic

import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ElasticEndpointSpec extends Specification implements DomainUnitTest<ElasticEndpoint> , DataTest{

    ElasticEndpoint elasticEndpoint
    String url
    String name

    @Override
    Class[] getDomainClassesToMock() {
        return [ElasticEndpoint]
    }

    def setup() {
        name = "aws-endpoint"
        url = "https://search-iris-ibwkuxcv4b2unly3c7d2d77v2a.eu-west-1.es.amazonaws.com"
    }

    def cleanup() {
    }

    void "test create elastic endpoint"() {
        when: "I create an elastic endpoint"
        elasticEndpoint = new ElasticEndpoint(name :name, url: url, active: true)

        and: "I save the endpoint"
        elasticEndpoint.save(flush: true)

        then: "it exists in the database"
        ElasticEndpoint.count() == 1
    }

    void "test edit elastic endpoint"(){
        when: "I create an elastic endpoint"
        elasticEndpoint = new ElasticEndpoint(name: name, url: url, active: true)

        and: "I save the endpoint"
        elasticEndpoint.save(flush: true)
        assert ElasticEndpoint.count() == 1

        and: "I edit the elastic endpoint"
        elasticEndpoint.active = false

        and: " I save the update"
        elasticEndpoint.save(flush: true)

        then: "I can find it by its new active state"
        assert ElasticEndpoint.findByActive(false) != null
    }

    void "test endpoint url constraint"(){
        when: "I create an elastic endpoint"
        elasticEndpoint = new ElasticEndpoint(name: name, url: url, active: true)

        and: "I save the endpoint"
        elasticEndpoint.save(flush: true)
        assert ElasticEndpoint.count() == 1

        and: "I edit the url"
        elasticEndpoint.url = url + "/something"

        then: " it is a valid"
        assert elasticEndpoint.validate()
    }

}
