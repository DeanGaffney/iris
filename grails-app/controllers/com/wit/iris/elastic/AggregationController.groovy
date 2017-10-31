package com.wit.iris.elastic

import com.wit.iris.schemas.Schema

class AggregationController {

    static scaffold = Aggregation

    def create(){
        List schemas = Schema.list()
        render(template: "create", model: [schemas : schemas])
    }

    def getAvgTemplate(){
        Schema schema = Schema.get(request.JSON.schemaId)
        List schemaFields = schema.schemaFields.findAll{it.fieldType != "String" && it.fieldType != "boolean" && it.fieldType != "Date"} as List
        render(template: "/aggregation/metric/avg", model:[schemaFields: schemaFields*.name])
    }

}
