package com.wit.iris.schemas

import com.wit.iris.com.wit.tests.domains.utils.DomainUtils
import com.wit.iris.schemas.enums.FieldType
import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class SchemaFieldSpec extends Specification implements DomainUnitTest<SchemaField> , DataTest{

    Schema schema
    SchemaField schemaField

    @Override
    Class[] getDomainClassesToMock() {
        return [Schema, SchemaField]
    }

    def setupData(){
        schema = DomainUtils.getSchemaWithSingleSchemaField()
        schemaField = schema.schemaFields[0]
        schema.save(flush: true, failOnError: true)

        assert Schema.count() == 1 && SchemaField.count() == 1
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
        assert schemaField.name == "counter"

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
        assert schemaField.name == "counter"

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
        assert schemaField.name == "counter"

        when: "I change the name to symbols"
        schemaField.setName("!@\$^#%*(){}[],;.")

        then: "it is not valid"
        !schemaField.validate()
    }

    void "test create SchemaField with null name"(){
        setup:
        setupData()
        assert schemaField.name == "counter"

        when: "I change the name to be null"
        schemaField.setName(null)

        then: "The object won't be valid"
        !schemaField.validate()
    }

    void "test create SchemaField with blank name"(){
        setup:
        setupData()
        assert schemaField.name == "counter"

        when: "I change the name to be blank"
        schemaField.setName("")

        then: "The object won't be valid"
        !schemaField.validate()
    }

}
