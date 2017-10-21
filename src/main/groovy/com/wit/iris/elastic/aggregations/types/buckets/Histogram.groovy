package com.wit.iris.elastic.aggregations.types.buckets

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.enums.BucketType
import com.wit.iris.elastic.aggregations.types.interfaces.Bucket
import com.wit.iris.elastic.aggregations.types.operation.Order

class Histogram extends AggregationType implements Bucket{

    String type = BucketType.HISTOGRAM.getValue()
    int interval
    Order order
    int minDocCount
    int missing
    int offset
    boolean keyed


    @Override
    void createAggregationMap(){
        Map operationMap = ["field" : super.getField(), "keyed" : keyed]
        if(missing){
            operationMap.put("missing", missing)
        }
        if(interval){
            operationMap.put("interval",interval)
        }
        if(offset){
            operationMap.put("offset",offset)
        }
        if(minDocCount){
            operationMap.put("min_doc_count", minDocCount)
        }
        if((order) && order.validate()){
            order.createOrderMap()
            operationMap += order.orderMap
        }
        this.aggregationMap = [(type):operationMap]
    }

}