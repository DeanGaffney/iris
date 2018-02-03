package com.wit.iris.schemas

import com.wit.iris.users.User
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification
import com.wit.iris.schemas.enums.FieldType


@Integration
@Rollback
class SchemaFieldSpec extends Specification {

    User user
    IrisSchema schema
    SchemaField schemaField

    def setupData(){
        user = new User(username: "deangaffney", password: "password")
        schema = new IrisSchema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 1000)
        schemaField = new SchemaField(name: "writeSpeed", fieldType: FieldType.DOUBLE.getValue())
        schema.addToSchemaFields(schemaField)
        user.addToSchemas(schema)
        user.save(flush: true)

        assert IrisSchema.count() == 1 && SchemaField.count() == 1
        assert User.count() == 1
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
