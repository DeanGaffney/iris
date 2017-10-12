package com.wit.iris.schemas

import com.wit.iris.enums.FieldType
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class SchemaSpec extends Specification implements DomainUnitTest<Schema> {

    def setup() {
    }

    def cleanup() {
    }

    void "create Schema"(){
        setup:
        new Schema(name: "Performance Monitor").save();

        expect:
        Schema.count() == 1;
    }

    void "create Schema with SchemaFields"(){
        setup:
        Schema schema = new Schema(name: "Performance Monitor");
        schema.addToSchemaFields(new SchemaField(name: "counter", fieldType: FieldType.INT.getValue()))
        schema.addToSchemaFields(new SchemaField(name: "writeTime", fieldType: FieldType.LONG.getValue()))
        schema.save()

        expect:
        Schema.count() == 1
        SchemaField.count() == 2
    }

    void "delete Schema with SchemaFields"(){
        setup:
        Schema schema = new Schema(name: "Performance Monitor");
        schema.addToSchemaFields(new SchemaField(name: "counter", fieldType: FieldType.INT.getValue(), schema: schema))
        schema.addToSchemaFields(new SchemaField(name: "writeTime", fieldType: FieldType.LONG.getValue(), schema: schema))
        schema.save()

        expect:
        Schema.count() == 1
        SchemaField.count() == 2

        when:
        schema = Schema.findByName("Performance Monitor")
        schema.delete(flush: true)

        then:
        Schema.count() == 0
        SchemaField.count() == 0
    }
}
