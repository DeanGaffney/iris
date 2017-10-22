package com.wit.iris.schemas


class SchemaController {

    static scaffold = Schema

    def schemaService

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

    def show(){
        render(view: "show", model: [schema: Schema.get(params.id)])
    }
}
