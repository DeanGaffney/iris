package com.wit.iris.schemas

import grails.gorm.transactions.Transactional

@Transactional
class SchemaService {

    def springSecurityService

    Schema createSchema(Schema schema){
        schema.esIndex = getEsIndexFromName(schema.name)
        schema.user = springSecurityService.getCurrentUser()
        if(!(schema.validate() && schema.save(flush: true))){
            println(schema.errors)
        }
        return schema
    }

    String getEsIndexFromName(String name){
        return name.toLowerCase().replaceAll(" ", "_")
    }

    Schema updateSchema(Schema schema){
        Schema updatedSchema = Schema.findByName(schema.name)
        updatedSchema.properties = schema.properties
        if(!(updatedSchema.validate() && updatedSchema.save(flush: true))){
            println(updatedSchema.errors)
        }
        return updatedSchema
    }

    void deleteSchema(Schema schema){
        schema.delete(flush: true)
    }

}
