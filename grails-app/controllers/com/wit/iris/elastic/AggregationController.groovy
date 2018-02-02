package com.wit.iris.elastic

import com.wit.iris.schemas.Schema
import grails.converters.JSON
import grails.plugins.rest.client.RestResponse
import groovy.json.JsonBuilder

import java.util.logging.Logger

class AggregationController {

    static scaffold = Aggregation

    def aggregationService

    def index(){
        List schemas = Schema.list()
        render(view: "index", model: [schemas: schemas])
    }

    def create(){
        List schemas = Schema.list()
        render(template: "create", model: [schemas : schemas])
    }

    /**
     * Get the result from executing an aggregation
     * @return the aggregation result as JSON
     */
    def getAggregationResult(){
        Schema schema = Schema.get(request.JSON.schemaId)
        String aggJson = new JsonBuilder(request.JSON.aggJson).toString()
        Aggregation agg = new Aggregation(esIndex: schema.esIndex, json: aggJson,
                                          levels: request.JSON.aggLevels)
        RestResponse result = aggregationService.execute(agg)
        render result.json as JSON
    }

    /**
     * Get the template for a specific metric aggregation
     * @return the metric aggregation template to the client
     */
    def getMetricTemplate(){
        //grab the selected schema
        Schema schema = Schema.get(request.JSON.schemaId)
        String aggType = request.JSON.aggType as String
        String template = aggregationService.getMetricTemplate(aggType)
        List schemaFields = aggregationService.getMetricFields(schema)
        render(template: template, model:[schemaFields: schemaFields*.name, hiddenValue: aggType])
    }

    /**
     * Get the template for a specific bucket aggregation
     * @return the bucket aggregation template to the client
     */
    def getBucketTemplate(){
        Schema schema = Schema.get(request.JSON.schemaId)
        String aggType = request.JSON.aggType as String
        String template = aggregationService.getBucketTemplate(aggType)
        List schemaFields = aggregationService.getBucketFields(schema, aggType)
        render(template: template, model:[schemaFields: schemaFields*.name, hiddenValue: aggType])
    }

}
