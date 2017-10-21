package com.wit.iris.elastic.aggregations.types.metric

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.enums.MetricType
import com.wit.iris.elastic.aggregations.types.interfaces.Metric

class Percentiles extends AggregationType implements Metric{

    String type = MetricType.PERCENTILES.getValue()
    List<Double> percents //TODO add this to analytics view
    int missing

    @Override
    void createAggregationMap(){
        //[percentiles : [field : "some_field", percents : [99,90,95], missing : 10]]
        Map operationMap = ["field" : super.getField()]
        if(missing != 0){
            operationMap.put("missing", missing)
        }
        if(percents != null){
            operationMap.put("percents", percents)
        }
        this.aggregationMap = [(type) : operationMap]
    }
}
