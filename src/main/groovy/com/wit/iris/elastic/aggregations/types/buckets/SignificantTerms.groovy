package com.wit.iris.elastic.aggregations.types.buckets

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.enums.BucketType
import com.wit.iris.elastic.aggregations.types.interfaces.Bucket

class SignificantTerms extends AggregationType implements Bucket{

    String type = BucketType.SIGNIFICANT_TERMS.getValue()
    int size //defaults 10 if not set
    int shardSize //defaults 10 if not set
    int minDocCount //only return a result set if minimum count is met.

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
        this.aggregationMap = [(type):operationMap]
    }

}