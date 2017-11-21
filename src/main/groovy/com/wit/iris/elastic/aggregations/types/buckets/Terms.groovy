package com.wit.iris.elastic.aggregations.types.buckets

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.enums.BucketType
import com.wit.iris.elastic.aggregations.types.interfaces.Bucket
import com.wit.iris.elastic.aggregations.types.operation.Order

class Terms extends AggregationType implements Bucket{

    String type = BucketType.TERMS.getValue()
    int size //defaults 10 if not set
    int shardSize //defaults 10 if not set
    int minDocCount //only return a result set if minimum count is met.
    Order order


    @Override
    void createAggregationMap(){
        Map operationMap = ["field" : super.getField()]
        if(size != 0){
            operationMap.put("size", size)
        }
        if(shardSize != 0){
            operationMap.put("shard_size", shardSize)
        }
        if(minDocCount != 0){
            operationMap.put("min_doc_count", minDocCount)
        }
        if((order) && order.validate()){
            order.createOrderMap()
            operationMap += order.orderMap
        }
        this.aggregationMap = [(type):operationMap]
    }

}
