package com.wit.iris.schemas

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification
import com.wit.iris.enums.FieldType


@Integration
@Rollback
class SchemaFieldSpec extends Specification {

    Schema schema
    SchemaField schemaField

    def setup() {
        schema = new Schema(name: "Performance")
        schemaField = new SchemaField(name: "counter", fieldType: FieldType.INT.getValue())
        schema.addToSchemaFields(schemaField)
        schema.save()

        assert Schema.count() == 1 && SchemaField.count() == 1
    }

    def cleanup() {
    }

    void "delete SchemaField"(){
        when:
        schema.removeFromSchemaFields(schemaField)

        and:
        schema.save(flush: true)

        then:
        assert SchemaField.count() == 0
    }
}
