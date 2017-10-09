package com.wit.iris.schemas

import com.wit.iris.enums.FieldType
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class SchemaFieldSpec extends Specification implements DomainUnitTest<SchemaField> {

    def setup() {
    }

    def cleanup() {
    }

    void "create SchemaField"(){
        setup:
        new SchemaField(name: "counter", fieldType: FieldType.INT.getValue()).save();

        expect:
        SchemaField.count() == 1;
    }

    void "delete SchemaField"(){
        setup:
        SchemaField schemaField = new SchemaField(name: "counter", fieldType:  FieldType.INT.getValue());
        schemaField.save();

        expect:
        SchemaField.count() == 1;

        when:
        schemaField.delete();

        then:
        SchemaField.count() == 0;
    }

    void "edit SchemaField"(){
        setup:
        SchemaField schemaField = new SchemaField(name: "counter", fieldType: FieldType.INT.getValue());
        schemaField.save();

        expect:
        SchemaField.count() == 1;

        and:
        schemaField.name == "counter";

        when:
        schemaField = SchemaField.findByName("counter");
        schemaField.setName("newName");

        and:
        schemaField.save();

        then:
        SchemaField.findByName("newName") != null;
    }
}
