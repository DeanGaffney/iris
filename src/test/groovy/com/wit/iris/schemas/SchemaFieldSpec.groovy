package com.wit.iris.schemas

import com.wit.iris.schemas.enums.FieldType
import com.wit.iris.users.User
import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class SchemaFieldSpec extends Specification implements DomainUnitTest<SchemaField> , DataTest{

    User user
    IrisSchema schema
    SchemaField schemaField

    @Override
    Class[] getDomainClassesToMock() {
        return [User, IrisSchema, SchemaField]
    }

    def setupData(){
        user = new User(username: "deangaffney", password: "password")
        schema = new IrisSchema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 10000)
        schemaField = new SchemaField(name: "writeSpeed", fieldType: FieldType.DOUBLE.getValue())
        schema.addToSchemaFields(schemaField)
        user.addToSchemas(schema)
        user.save(flush:true)

        assert User.count() == 1
        assert IrisSchema.count() == 1
        assert SchemaField.count() == 1
    }

    def setup() {

    }

    def cleanup() {
    }

    void "test create SchemaField"(){
        setup:
        setupData()
        schema.addToSchemaFields(new SchemaField(name: "records", fieldType: FieldType.INT.getValue()))

        and:
        schema.save(flush: true, failOnError: true)

        expect:
        SchemaField.count() == 2
    }


    void "test edit SchemaField"(){
        setup:
        setupData()

        when: "I change the name"
        schemaField.setName("newName")

        and: "Save the SchemaField"
        schemaField.save(flush: true, failOnError: true)

        then: "I can find it using the new name"
        SchemaField.findByName("newName") != null
    }

    void "test name matches constraint"(){
        setup:
        setupData()

        when: "I change the name to camel case"
        schemaField.setName("someCounter")

        then: "It is valid"
        schemaField.validate()

        when: "I change the name to have underscores"
        schemaField.setName("some_counter")

        then: "it is valid"
        schemaField.validate()

        when: "I change the name to include numbers"
        schemaField.setName("counter1")

        then: "it is valid"
        schemaField.validate()

        when: "I change the name to be uppercase"
        schemaField.setName("COUNTER")

        then: "it is valid"
        schemaField.validate()
    }

    void "test create SchemaField with non matching name"(){
        setup:
        setupData()

        when: "I change the name to symbols"
        schemaField.setName("!@\$^#%*(){}[],;.")

        then: "it is not valid"
        !schemaField.validate()
    }

    void "test create SchemaField with null name"(){
        setup:
        setupData()

        when: "I change the name to be null"
        schemaField.setName(null)

        then: "The object won't be valid"
        !schemaField.validate()
    }

    void "test create SchemaField with blank name"(){
        setup:
        setupData()

        when: "I change the name to be blank"
        schemaField.setName("")

        then: "The object won't be valid"
        !schemaField.validate()
    }


    void "test comparable with list"(){
        when: "I create a list of SchemaFields"
        List<SchemaField> schemaFields1 = [new SchemaField(name: "readSpeed", fieldType: FieldType.INT.getValue())]

        and: "I create a new list of schema fields"
        List<SchemaField> schemaFields2 = [new SchemaField(name: "readSpeed", fieldType: FieldType.INT.getValue()), new SchemaField(name: "flushSpeed", fieldType: FieldType.INT.getValue())]

        then: "I can get the difference of the list with subtraction"
        assert [new SchemaField(name: "flushSpeed", fieldType: FieldType.INT.getValue())] == schemaFields2 - schemaFields1

    }
}
