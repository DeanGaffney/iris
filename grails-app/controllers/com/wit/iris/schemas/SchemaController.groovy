package com.wit.iris.schemas

import com.wit.iris.charts.Chart
import com.wit.iris.dashboards.Dashboard
import grails.converters.JSON
import grails.plugins.rest.client.RestResponse

class SchemaController {

    static scaffold = Schema

    def schemaService
    def routeService
    def aggregationService
    def chartService
    def dashboardService

    def index(){
        List<Schema> schemas = Schema.list()
        render(view: "index", model:[schemas: schemas])
    }

    def create(){
        render(template: "create")
    }

    def save(Schema schema){
        schemaService.createSchema(schema)
        redirect(view: "index")
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

    def show(long id){
        Schema schema = Schema.get(id)
        render(template: "show", model: [schema: schema])
    }

    /**
     * Takes in data for transformation and routing to elasticsearch
     * data is also sent to any charts needing to be updated
     */
    def route(long id){
        Map resp = ["status": 200, "message": "data inserted"]
        Schema schema = Schema.get(id)
        if(schema == null){
            resp.status = 500
            resp.message = "Schema with id $id does not exist"
        }else{
            resp = routeService.route(schema, request.JSON).json as Map        //route and transform data
            //get all dashboards that are currently marked as rendering
            dashboardService.updateDashboardCharts(id)
        }
        render resp as JSON
    }
}
