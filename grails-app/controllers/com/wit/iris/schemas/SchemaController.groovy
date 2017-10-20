package com.wit.iris.schemas

import grails.converters.JSON
import org.grails.web.json.JSONObject

class SchemaController {

    static scaffold = Schema

    def schemaService

    def save(){
        JSONObject schemaJson = request.JSON
        Schema schema = schemaService.createSchema(schemaJson)
        Map response = ['flashType': "success", 'message': "Save successful"]
        render response as JSON
    }

    def show(){
        render(view: "show", model: [schema: Schema.get(params.id)])
    }
}
