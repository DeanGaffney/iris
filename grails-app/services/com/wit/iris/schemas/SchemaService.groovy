package com.wit.iris.schemas

import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject

@Transactional
class SchemaService {

    def springSecurityService

    Schema createSchema(JSONObject schemaJson){
        String esIndex = schemaJson.get("name").toLowerCase().replaceAll(" ", "_")
        Schema schema = new Schema(name: schemaJson.get("name"), refreshInterval: schemaJson.get("refreshInterval"),
                                   esIndex: esIndex, user: springSecurityService.getCurrentUser(),
                                   schemaFields: schemaJson.get("schemaFields"))
        if(!(schema.validate() && schema.save(flush: true))){
            println(schema.errors)
        }
        return schema
    }

}
