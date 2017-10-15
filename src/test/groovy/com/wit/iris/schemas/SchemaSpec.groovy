package com.wit.iris.schemas

import com.wit.iris.schemas.enums.FieldType
import com.wit.iris.users.User
import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class SchemaSpec extends Specification implements DomainUnitTest<Schema>, DataTest{

    User user
    Schema schema
    SchemaField schemaField

    @Override
    Class[] getDomainClassesToMock() {
        return [User, Schema, SchemaField]
    }

    def setupData(){
        user = new User(username: "deangaffney", password: "password")
        schema = new Schema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 10000)
        schemaField = new SchemaField(name: "writeSpeed", fieldType: FieldType.DOUBLE.getValue())
        schema.addToSchemaFields(schemaField)
        user.addToSchemas(schema)
        user.save(flush:true)

        assert User.count() == 1
        assert Schema.count() == 1
        assert SchemaField.count() == 1
    }

    def setup() {

    }

    def cleanup() {
    }

    void "test create Schema"(){
        setup: "I create a new Schema"
        setupData()
        user.addToSchemas(new Schema(name: "Other monitor", esIndex: "other_monitor", refreshInterval: 5000)).save()

        expect: "The count to be 2"
        Schema.count() == 2
    }

    void "test create Schema with SchemaFields"(){
        setup: "I add Schemafields to my Schema"
        setupData()
        schema.addToSchemaFields(new SchemaField(name: "counter", fieldType: FieldType.INT.getValue()))
        schema.addToSchemaFields(new SchemaField(name: "writeTime", fieldType: FieldType.LONG.getValue()))

        and: "I save my Schema"
        schema.save()

        expect: "The SchemaFields to be saved with the Schema"
        Schema.count() == 1
        SchemaField.count() == 3
    }

    void "test delete Schema"(){
        setup:
        setupData()

        when: "I delete a Schema"
        schema.delete(flush: true)

        then: "It is deleted from the database"
        Schema.count() == 0
    }

    void "test edit Schema"(){
        setup:
        setupData()

        when: "I edit an existing Schema"
        schema.name = "other monitor"

        and: "I save the new edited schema"
        schema.save()

        then: "I can find the schema by its new name"
        Schema.findByName("other monitor") != null
    }

    void "test Schema name constraints"(){
        setup:
        setupData()

        when: "I change the name to null"
        schema.name = null

        then: "It is not valid"
        !schema.validate()

        when: "I change the name to be blank"
        schema.name = ""

        then: "It is not valid"
        !schema.validate()

        when: "I change the name to be over 100 characters long"
        schema.name = "a" * 101

        then: "It is not valid"
        !schema.validate()

        when: "I change the name to be exactly 100 characters or less"
        schema.name = "a" * 100

        then: "It is valid"
        schema.validate()

        when: "I create a Schema with an existing schema name(not unique)"
        Schema notUniqueSchema = new Schema(name: "Performance Monitor", esIndex: "other_monitor", refreshInterval: 1000)

        then: "It wont be valid"
        !notUniqueSchema.validate()

        when: "I change the name to be unique"
        schema.name = "Other Monitor"

        then: "It is valid"
        schema.validate()

        when: "I change the name to be special symbols"
        schema.name = "asd%^&£"

        then: "It is not valid"
        !schema.validate()

        when: "I change the name to match the regex constraint"
        schema.name = "some_monitor_1"

        then: "It is valid"
        schema.validate()

    }

    void "test Schema esIndex constraints"(){
        setup:
        setupData()

        when: "I change the esIndex to be null"
        schema.esIndex = null

        then: "It is not valid"
        !schema.validate()

        when: "I change the esIndex to be blank"
        schema.esIndex = ""

        then: "It is not valid"
        !schema.validate()

        when: "I create a new Schema with a non unique esIndex"
        Schema nonUniqueSchema = new Schema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 1000)

        then: "It is not valid"
        !nonUniqueSchema.validate()

        when: "I change the esIndex to not match the naming convention"
        schema.name = "43523%^&"

        then: "It is not valid"
        !schema.validate()
    }

    void "test Schema refreshInterval constraints"(){
        setup:
        setupData()

        when: "I change the refreshInterval to be null"
        schema.refreshInterval = null

        then: "It is not valid"
        !schema.validate()
    }
}
