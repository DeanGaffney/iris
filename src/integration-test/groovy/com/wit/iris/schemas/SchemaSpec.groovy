package com.wit.iris.schemas

import com.wit.iris.schemas.enums.FieldType
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class SchemaSpec extends Specification {

    Schema schema
    SchemaField schemaField

    def setupData(){
        schema = schema = new Schema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 1000)
        schemaField = new SchemaField(name: "writeSpeed", fieldType: FieldType.DOUBLE.getValue())
        schema.addToSchemaFields(schemaField)
        schema.save(flush: true)

        assert Schema.count() == 1 && SchemaField.count() == 1
    }

    def cleanup() {
    }


    void "test cascade delete on Schema"(){
        setup:
        setupData()

        when: "I delete the schema object"
        schema.delete(flush: true)

        then: "The SchemaField child is also deleted"
        assert Schema.count() == 0
        assert SchemaField.count() == 0
    }

    void "test cascade delete on Schema children"(){
        setup:
        setupData()

        when: "I remove a Schema Field from the schema"
        schema.removeFromSchemaFields(schemaField)

        and: "I save the schema object"
        schema.save(flush: true)

        then: "The schema field is deleted"
        assert Schema.count() == 1
        assert schema.schemaFields.size() == 0
        assert SchemaField.count() == 0
    }

    void "test update cascade on Schema"(){
        setup:
        setupData()

        when: "I edit the schema field child"
        schemaField.name = "readSpeed"

        and: "I save the child"
        schemaField.save()

        then: "I can find the child by its new name from the parent"
        assert schema.schemaFields.find {it.name == "readSpeed"} != null
    }

    void "test cascade update of SchemaField on Schema save"(){
        setup:
        setupData()

        when: "I edit a Schema Field"
        schemaField.name = "readSpeed"

        and: "I save the Schema"
        schema.save(flush: true)

        then: "The update to the schema field is saved too"
        assert SchemaField.findByName("readSpeed") != null
    }

}
