package com.wit.iris.schemas

import com.wit.iris.schemas.enums.IrisSchemaField
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

        //add iris insertion date as a field to the schema
        schema.schemaFields.add(new SchemaField(name: IrisSchemaField.INSERTION_DATE.getFieldName(),
                                                fieldType: IrisSchemaField.INSERTION_DATE.getFieldType()))

        schema.user = springSecurityService.getCurrentUser()
        if(!(schema.validate() && schema.save(flush: true))){
            log.debug(schema.errors.allErrors*.toString())
        }else{
            elasticService.createIndex(schema)     //create elastic search index and mapping
        }
        return schema
    }

    /**
     * Updates a schema domain and its elasticsearch mapping
     * @param schema, the schema to update
     * @return The updated version of the schema
     */
    Schema updateSchema(Schema schema){
        Schema legacySchema = Schema.findByName(schema.name)
        //get list before clearing
        List<SchemaField> legacyFields = legacySchema.schemaFields.toList()
        List<SchemaField> updatedFields = schema.schemaFields.toList()

        //clear fields and new updated fields
        legacySchema.schemaFields.clear()
        schema.schemaFields.each{legacySchema.addToSchemaFields(it)}


        if(!(legacySchema.validate() && legacySchema.save(flush: true))){
            println(legacySchema.errors)
        }else{
            //update the mapping
            elasticService.updateMapping(legacySchema.esIndex, legacyFields, updatedFields)
        }
        return legacySchema
    }

    /**
     * Deletes a schema and its elasticsearch index
     * @param schema, the schema to delete
     */
    void deleteSchema(Schema schema){
        String esIndexName = schema.esIndex     //when I add an if statement to see if it has been deleted it doesnt work as expected //TODO look into this
        schema.delete(flush: true)
        elasticService.deleteIndex(esIndexName)      //delete the elasticsearch index
    }

}
