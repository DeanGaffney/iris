package com.wit.iris.elastic.aggregations.types.buckets

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.enums.BucketType
import com.wit.iris.elastic.aggregations.types.interfaces.Bucket

class Ranges extends AggregationType implements Bucket{

    String type = BucketType.RANGE.getValue()
    List<Range> ranges
    boolean keyed //Setting the keyed flag to true will associate a unique string key with each bucket and return the ranges as a hash rather than an array

    @Override
    void createAggregationMap(){
        Map operationMap = ["field" : field, "keyed" : keyed]
        def rangeList = []
        ranges.each { Range range ->
            if(range.validate()){
                rangeList.add(range.createRangeObject())
            }
        }
        if(rangeList.size() > 0){
            operationMap.put("ranges",rangeList)
        }
        this.aggregationMap = [(type):operationMap]
    }

}