package com.wit.iris.elastic

import com.wit.iris.elastic.aggregations.types.enums.*
import com.wit.iris.schemas.enums.FieldType
import com.wit.iris.schemas.IrisSchema
import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestResponse
import groovy.json.JsonOutput

import java.util.regex.Matcher

@Transactional
class AggregationService {

    def elasticService

    /**
     * -------------------------------------------------------------------------
     *                            AGGREGATIONS
     *  -------------------------------------------------------------------------
     */

    /**
     * Executes an aggregation on an elasticsearch index
     * @param agg - the aggregation object to execute
     * @return aggregation result data from elasticsearch
     */
    RestResponse execute(Aggregation agg){
        log.debug("Aggregation Executing:\n${JsonOutput.prettyPrint(agg.json)}")
        return elasticService.executeAggregation(agg)
    }

    int countAggregationLevels(String json){
        Matcher matcher = (json =~ /"aggs"/)
        return matcher.getCount()
    }

    /**
     * -------------------------------------------------------------------------
     *                            METRICS
     *  -------------------------------------------------------------------------
     */

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
    List getMetricFields(IrisSchema schema){
        return schema.schemaFields.findAll{ it.fieldType != FieldType.STRING.getValue() &&
                                            it.fieldType != FieldType.BOOLEAN.getValue() &&
                                            it.fieldType != FieldType.DATE.getValue()} as List
    }

    /**
     * -------------------------------------------------------------------------
     *                            BUCKETS
     *  -------------------------------------------------------------------------
     */

    /**
     * Gets the path to the template for the specific bucket aggregation type
     * @param aggType - the type of bucket aggregation i.e terms, histogram etc....
     * @return The path to the specific bucket template
     */
    String getBucketTemplate(String aggType){
        String template = ""
        if(aggType == BucketType.TERMS.getValue()){
            template = "/aggregation/bucket/terms"
        }
        return template
    }

    /**
     * Get a list of all fields in schema suited for the aggregation type
     * @param schema - the schema to get the fields from
     * @param aggType - the type of aggregation (terms, date histogram etc.....)
     * @return List of schema fields suited for the aggregation type
     */
    List getBucketFields(IrisSchema schema, aggType){
        List fields = Collections.emptyList()
        if(aggType == BucketType.TERMS.getValue()){
            fields = getTermsFields(schema)
        }
        return fields
    }

    /**
     * Gets all SchemaFields from a Schema that are suited for the Terms aggregation
     * @param schema - schema to get the fields from
     * @return List of Schema Fields
     */
    List getTermsFields(IrisSchema schema){
        return schema.schemaFields.findAll{ it.fieldType == FieldType.STRING.getValue()} as List
    }

}
