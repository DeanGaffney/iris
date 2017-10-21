package com.wit.iris.schemas

import com.wit.iris.utils.JsonParser
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject

@Transactional
class SchemaService {

    def springSecurityService
    JsonParser parser = new JsonParser()

    Schema createSchema(JSONObject schemaJson){
        Object result = parser.parse(schemaJson)
        String esIndex = getEsIndexFromName(result.name)
        Schema schema = new Schema(name: result.name, refreshInterval: result.refreshInterval,
                                   esIndex: esIndex, user: springSecurityService.getCurrentUser(),
                                   schemaFields: result.schemaFields)
        if(!(schema.validate() && schema.save(flush: true))){
            println(schema.errors)
        }
        return schema
    }

    String getEsIndexFromName(String name){
        return name.toLowerCase().replaceAll(" ", "_")
    }

    Schema updateSchema(JSONObject schemaJson){
        Object result = parser.parse(schemaJson)
        Schema schema = Schema.get(result.id as Long)
        schema.name = result.name as String
        schema.esIndex = getEsIndexFromName(schema.name)
        schema.refreshInterval = result.refreshInterval as Long
        schema.schemaFields = result.schemaFields.collect{
            println(it)
            println(it.name)
            println(it.fieldType)
            return new SchemaField(name: it.name, fieldType: it.fieldType, schema: schema)
        }

        if(!(schema.validate() && schema.save(flush: true))){
            println(schema.errors)
        }
        return schema
    }

}
