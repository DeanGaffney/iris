package com.wit.iris.elastic.aggregations.types.metric

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.enums.MetricType
import com.wit.iris.elastic.aggregations.types.interfaces.Metric

class PercentileRanks extends AggregationType implements Metric{

    String type = MetricType.PERCENTILE_RANKS.getValue() //TODO add this to analytics view
    List<Integer> values
    int missing



    @Override
    void createAggregationMap(){
        // "percentile_ranks" : {"field" : "load_time","values" : [15, 30]}
        Map operationMap = ["field" : super.getField(), "values" : values]
        if(missing != 0){
            operationMap.put("missing", missing)
        }
        this.aggregationMap = [(type) : operationMap]
    }

}
