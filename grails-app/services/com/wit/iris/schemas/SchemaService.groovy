package com.wit.iris.schemas

import grails.gorm.transactions.Transactional

@Transactional
class SchemaService {

    def springSecurityService
    def elasticService

    /**
     * Creates a schema domain and its respective elasticsearch index
     * @param schema, the schema to save
     * @return the saved schema instance
     */
    Schema createSchema(Schema schema){
        schema.esIndex = elasticService.getIndexFromName(schema.name)
        schema.user = springSecurityService.getCurrentUser()
        if(!(schema.validate() && schema.save(flush: true))){
            println(schema.errors)
        }
        elasticService.createIndex(schema)     //create elastic search index and mapping
        return schema
    }

    /**
     * Updates a schema domain and its elasticsearch mapping
     * @param schema, the schema to update
     * @return The updated version of the schema
     */
    Schema updateSchema(Schema schema){
        Schema updatedSchema = Schema.findByName(schema.name)
        updatedSchema.properties = schema.properties
        if(!(updatedSchema.validate() && updatedSchema.save(flush: true))){
            println(updatedSchema.errors)
        }
        return updatedSchema
    }

    /**
     * Deletes a schema and its elasticsearch index
     * @param schema, the schema to delete
     */
    void deleteSchema(Schema schema){
        elasticService.deleteIndex(schema)      //delete the elasticsearch index
        if(!schema.delete(flush: true)){
            println(schema.errors)
        }
    }

}
