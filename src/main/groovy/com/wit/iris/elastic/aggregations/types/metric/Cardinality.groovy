package com.wit.iris.elastic.aggregations.types.metric

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.enums.MetricType
import com.wit.iris.elastic.aggregations.types.interfaces.Metric

class Cardinality extends AggregationType implements Metric{

    String type = MetricType.CARDINALITY.getValue()
    String missing              //N/A is an option for cardinality rather than int like in others

    @Override
    void createAggregationMap(){
        Map operationMap = ["field" : super.getField()]
        if(missing){
            operationMap.put("missing", missing)
        }
        this.aggregationMap = [(type) : operationMap]
    }

}
