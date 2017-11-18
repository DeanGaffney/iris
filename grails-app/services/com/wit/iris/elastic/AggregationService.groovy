package com.wit.iris.elastic

import com.wit.iris.elastic.aggregations.types.enums.MetricType
import com.wit.iris.schemas.Schema
import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestResponse
import org.grails.web.json.JSONObject

@Transactional
class AggregationService {

    def elasticService

    /**
     * Executes an aggregation on an elasticsearch index
     * @param esIndex - index to execute aggregation
     * @param agg - the aggregation object to execute
     * @return aggregation result data from elasticsearch
     */
    RestResponse execute(String esIndex, Aggregation agg){
       return elasticService.executeAggregation(esIndex, agg)
    }

    /**
     * Gets the template for the specific metric type
     * @param aggType - the type of metric aggregation i.e sum, avg , etc....
     * @return The path to the template
     */
    String getMetricTemplate(String aggType){
        String templatePath = "";
        if(isCommonMetric(aggType)){
            templatePath = "/aggregation/metric/common"
        }
        return templatePath
    }

    /**
     * Check to see if the metric type is a common type i.e use the same template
     * @param aggType - the type of aggregation i.e sum, avg , etc.....
     * @return true if it is a common metric, false otherwise
     */
    private isCommonMetric(String aggType){
        return  aggType == MetricType.AVERAGE.getValue()        ||
                aggType == MetricType.CARDINALITY.getValue()    ||
                aggType == MetricType.EXTENDED_STATS.getValue() ||
                aggType == MetricType.MAX.getValue()            ||
                aggType == MetricType.MIN.getValue()            ||
                aggType == MetricType.STATS.getValue()          ||
                aggType == MetricType.SUM.getValue()            ||
                aggType == MetricType.VALUE_COUNT.getValue()
    }

    /**
     * Gets all the numeric fields in a schema
     * @param schema - the schema to get the fields from
     * @return A List of schema fields which have fieldType that is numeric
     */
    List getMetricFields(Schema schema){
        return schema.schemaFields.findAll{ it.fieldType != "String" &&
                                            it.fieldType != "boolean" &&
                                            it.fieldType != "Date"} as List
    }

    /**
     * Gets the path to the template for the specific bucket aggregation type
     * @param aggType - the type of bucket aggregation i.e terms, histogram etc....
     * @return The path to the specific bucket template
     */
    String getBucketTemplate(String aggType){
        String template = ""
        if(aggType == "terms"){
            template = "/aggregation/bucket/terms"
        }

        return template
    }

    List getBucketFields(Schema schema, aggType){

    }
}
