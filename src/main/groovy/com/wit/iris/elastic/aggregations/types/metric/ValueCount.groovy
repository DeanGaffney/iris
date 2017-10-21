package com.wit.iris.elastic.aggregations.types.metric

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.enums.MetricType
import com.wit.iris.elastic.aggregations.types.interfaces.Metric

class ValueCount extends AggregationType implements Metric{

    String type = MetricType.VALUE_COUNT.getValue()

    @Override
    void createAggregationMap(){
        this.aggregationMap = [(type) : ["field" : super.getField()]]
    }
}
