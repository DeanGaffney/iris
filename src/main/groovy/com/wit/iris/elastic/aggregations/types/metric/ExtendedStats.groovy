package com.wit.iris.elastic.aggregations.types.metric

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.enums.MetricType
import com.wit.iris.elastic.aggregations.types.interfaces.Metric

class ExtendedStats extends AggregationType implements Metric{

    String type = MetricType.EXTENDED_STATS.getValue()
    int missing

    @Override
    void createAggregationMap(){
        Map operationMap = ["field" : super.getField()]
        if(missing != 0){
            operationMap.put("missing", missing)
        }
        this.aggregationMap = [(type) : operationMap]
    }

}
