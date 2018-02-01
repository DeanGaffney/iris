package com.wit.iris.elastic

import grails.test.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ElasticEndpointServiceSpec extends Specification {

    ElasticEndpointService elasticEndpointService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ElasticEndpoint(...).save(flush: true, failOnError: true)
        //new ElasticEndpoint(...).save(flush: true, failOnError: true)
        //ElasticEndpoint elasticEndpoint = new ElasticEndpoint(...).save(flush: true, failOnError: true)
        //new ElasticEndpoint(...).save(flush: true, failOnError: true)
        //new ElasticEndpoint(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //elasticEndpoint.id
    }

    void "test get"() {
        setupData()

        expect:
        elasticEndpointService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ElasticEndpoint> elasticEndpointList = elasticEndpointService.list(max: 2, offset: 2)

        then:
        elasticEndpointList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        elasticEndpointService.count() == 5
    }

    void "test delete"() {
        Long elasticEndpointId = setupData()

        expect:
        elasticEndpointService.count() == 5

        when:
        elasticEndpointService.delete(elasticEndpointId)
        sessionFactory.currentSession.flush()

        then:
        elasticEndpointService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ElasticEndpoint elasticEndpoint = new ElasticEndpoint()
        elasticEndpointService.save(elasticEndpoint)

        then:
        elasticEndpoint.id != null
    }
}
