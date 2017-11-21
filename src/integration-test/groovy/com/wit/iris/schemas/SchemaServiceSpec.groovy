package com.wit.iris.schemas

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class SchemaServiceSpec extends Specification {

    SchemaService schemaService

    def setup() {
    }

    def cleanup() {
    }


}
