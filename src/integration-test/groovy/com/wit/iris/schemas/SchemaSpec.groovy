package com.wit.iris.schemas

import com.wit.iris.enums.FieldType
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class SchemaSpec extends Specification {

    Schema schema
    SchemaField schemaField

    def setup() {
        schema = new Schema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 10000)
        schemaField = new SchemaField(name: "writeSpeed", fieldType: FieldType.DOUBLE.getValue())
        schema.addToSchemaFields(schemaField)
        schema.save()

        assert Schema.count() == 1 && SchemaField.count() == 1
    }

    def cleanup() {
    }

    void "test cascade delete on Schema"(){
        when: "I delete the schema object"
        schema.delete(flush: true)

        then: "The SchemaField child is also deleted"
        Schema.count() == 0 && SchemaField.count() == 0
    }

}
