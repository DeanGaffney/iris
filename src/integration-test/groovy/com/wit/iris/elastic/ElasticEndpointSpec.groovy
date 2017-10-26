package com.wit.iris.elastic

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class ElasticEndpointSpec extends Specification {

    ElasticEndpoint elasticEndpoint
    ElasticEndpoint elasticEndpoint1
    String url
    String name


    def setup() {
        name = "aws-endpoint"
        url = "https://search-iris-ibwkuxcv4b2unly3c7d2d77v2a.eu-west-1.es.amazonaws.com"
    }

    def cleanup() {
    }
    
}
