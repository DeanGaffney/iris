package com.wit.iris.elastic

import com.wit.iris.schemas.Schema

class AggregationController {

    static scaffold = Aggregation

    def aggregationService

    def create(){
        List schemas = Schema.list()
        render(template: "create", model: [schemas : schemas])
    }

    def getMetricTemplate(){
        //grab the selected schema
        Schema schema = Schema.get(request.JSON.schemaId)
        String aggType = request.JSON.aggType as String
        String template = aggregationService.getMetricTemplate(aggType)
        List schemaFields = aggregationService.getMetricFields(schema)
        render(template: template, model:[schemaFields: schemaFields*.name, hiddenValue: aggType])
    }

}
