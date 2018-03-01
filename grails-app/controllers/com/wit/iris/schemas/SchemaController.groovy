package com.wit.iris.schemas

import grails.converters.JSON

class SchemaController {

    static scaffold = IrisSchema

    def schemaService
    def routeService
    def dashboardService


    def index(){
        List<IrisSchema> schemas = IrisSchema.list()
        render(view: "index", model:[schemas: schemas])
    }

    def create(){
        render(template: "create")
    }

    def save(IrisSchema schema){
        schemaService.createSchema(schema)
        redirect(view: "index")
    }

    def edit(){
        IrisSchema schema = IrisSchema.get(request.JSON.schemaId)
        render(template: "edit", model: [schema: schema])
    }

    def update(IrisSchema schema){
        IrisSchema updatedSchema = schemaService.updateSchema(schema)
        render(template: "show", model: [schema: updatedSchema])
    }

    def delete(){
        IrisSchema schema = IrisSchema.get(params.id)
        schemaService.deleteSchema(schema)
        redirect(view: "index")
    }

    def show(long id){
        IrisSchema schema = IrisSchema.get(id)
        String schemaJson = schemaService.getExpectedJson(schema)
        render(template: "show", model: [schema: schema, schemaJson: schemaJson])
    }

    /**
     * Takes in data for transformation and routing to elasticsearch
     * data is also sent to any charts needing to be updated
     */
    def route(long id){
        Map resp = ["status": 200, "message": "data inserted"]
        IrisSchema schema = IrisSchema.get(id)
        if(schema == null){
            resp.status = 500
            resp.message = "Schema with id $id does not exist"
        }else{
            resp = routeService.route(schema, request.JSON).json as Map        //route and transform data
            //get all dashboards that are currently marked as rendering
            dashboardService.updateDashboardCharts(id, request.JSON)
        }
        render resp as JSON
    }

    /**
     * Accepts a json request from an agent,
     * the json request should contain a 'name' key to search for a schema by
     * the response will then be the unique schema endpoint for the agent
     */
    def getAgentUrl(){
        Map resp = [status: 200, url: ""]
        IrisSchema schema = IrisSchema.findWhere(name: request.JSON.name, archived: false)
        if(schema){
            String serverBase = getGrailsLinkGenerator().getServerBaseURL()
           resp.url = serverBase + g.createLink(controller: "schema", action: "route") + "/" + schema.id
        }else{
            resp.status = 500
            resp.url = "N/A"
        }
        render resp as JSON
    }

    def getSchemaFields(){
        long schemaId = request.JSON.schemaId as Long
        IrisSchema schema = IrisSchema.get(schemaId)
        List schemaFields = schema.schemaFields as List
        render(template: "schemaFieldSelect", model: [schemaFields: schemaFields])
    }
}
