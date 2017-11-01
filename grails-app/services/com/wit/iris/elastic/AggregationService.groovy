package com.wit.iris.elastic

import com.wit.iris.elastic.aggregations.types.enums.MetricType
import com.wit.iris.schemas.Schema
import grails.gorm.transactions.Transactional

@Transactional
class AggregationService {

    String getMetricTemplate(String aggType){
        String templatePath = "";
        if(isCommonMetric(aggType)){
            templatePath = "/aggregation/metric/common"
        }
        return templatePath
    }

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

    List getMetricFields(Schema schema){
        return schema.schemaFields.findAll{ it.fieldType != "String" &&
                                            it.fieldType != "boolean" &&
                                            it.fieldType != "Date"} as List
    }
}
