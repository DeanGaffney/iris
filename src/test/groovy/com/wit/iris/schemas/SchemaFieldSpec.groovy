package com.wit.iris.schemas

import com.wit.iris.enums.FieldType
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class SchemaFieldSpec extends Specification implements DomainUnitTest<SchemaField> {

    Schema schema

    def setup() {
        schema = new Schema(name: "Performance")
        schema.addToSchemaFields(new SchemaField(name: "counter", fieldType: FieldType.INT.getValue()))
        schema.save(flush: true, failOnError: true)

        assert Schema.count() == 1 && SchemaField.count() == 1
    }

    def cleanup() {
    }

    void "create SchemaField"(){
        setup:
        schema.addToSchemaFields(new SchemaField(name: "records", fieldType: FieldType.INT.getValue()))

        and:
        schema.save(flush: true, failOnError: true)

        expect:
        SchemaField.count() == 2
    }


    void "edit SchemaField"(){
        setup:
        SchemaField schemaField = SchemaField.findByName("counter")

        and:
        schemaField.name == "counter"

        when:
        schemaField.setName("newName")

        and:
        schemaField.save(flush: true, failOnError: true)

        then:
        SchemaField.findByName("newName") != null
    }
}
