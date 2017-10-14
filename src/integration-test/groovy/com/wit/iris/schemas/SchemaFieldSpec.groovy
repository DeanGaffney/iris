package com.wit.iris.schemas

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification
import com.wit.iris.schemas.enums.FieldType


@Integration
@Rollback
class SchemaFieldSpec extends Specification {

    Schema schema
    SchemaField schemaField

    def setupData(){
        schema = schema = new Schema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 1000)
        schemaField = new SchemaField(name: "writeSpeed", fieldType: FieldType.DOUBLE.getValue())
        schema.addToSchemaFields(schemaField)
        schema.save(flush: true)

        assert Schema.count() == 1 && SchemaField.count() == 1
    }

    def setup() {

    }

    def cleanup() {
    }

    void "delete SchemaField"(){
        setup:
        setupData()

        when:
        schema.removeFromSchemaFields(schemaField)

        and:
        schema.save(flush: true)

        then:
        assert SchemaField.count() == 0
    }
}
