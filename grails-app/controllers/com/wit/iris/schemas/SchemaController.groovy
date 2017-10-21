package com.wit.iris.schemas

import org.grails.web.json.JSONObject

class SchemaController {

    static scaffold = Schema

    def schemaService

    def create(){
        render(template: "create")
    }

    def save(){
        JSONObject schemaJson = request.JSON
        Schema schema = schemaService.createSchema(schemaJson)
        render(template: "show", model: [schema: schema])
    }

    def edit(){
        Schema schema = Schema.get(request.JSON.schemaId)
        render(template: "edit", model: [schema: schema])
    }

    def update(){
        //called form edit view, take in object and edit it,
        //return the showw view then off he newly edited object
        JSONObject schemaJson = request.JSON
        Schema schema = schemaService.updateSchema(schemaJson)
        render(template: "show", model: [schmea: schema])
    }

    def show(){
        render(view: "show", model: [schema: Schema.get(params.id)])
    }
}
