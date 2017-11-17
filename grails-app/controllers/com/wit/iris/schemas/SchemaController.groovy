package com.wit.iris.schemas

import com.wit.iris.charts.Chart
import grails.converters.JSON


class SchemaController {

    static scaffold = Schema

    def schemaService
    def routeService

    def index(){
        List<Schema> schemas = Schema.list()
        render(view: "index", model:[schemas: schemas])
    }

    def create(){
        render(template: "create")
    }

    def save(Schema schema){
        Schema savedSchema = schemaService.createSchema(schema)
        render(template: "show", model: [schema: savedSchema])
    }

    def edit(){
        Schema schema = Schema.get(request.JSON.schemaId)
        render(template: "edit", model: [schema: schema])
    }

    def update(Schema schema){
        Schema updatedSchema = schemaService.updateSchema(schema)
        render(template: "show", model: [schema: updatedSchema])
    }

    def delete(){
        Schema schema = Schema.get(params.id)
        schemaService.deleteSchema(schema)
        redirect(view: "index")
    }

    def show(){
        render(view: "show", model: [schema: Schema.get(params.id)])
    }

    /**
     * Takes in data to transform and routing to elasticsearch
     */
    def route(){
        Map resp = ["status": 200, "message": "data inserted"]
        Schema schema = Schema.get(request.JSON.schema.id)
        if(schema == null){
            resp.status = 500
            resp.message = "schema with id $request.JSON.schema.id does not exsist"
        }else{
            routeService.route(schema, request.JSON)
            Chart.findAllBy
        }
        render resp as JSON
    }
}
