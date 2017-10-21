package com.wit.iris.elastic.aggregations.types.buckets

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.enums.BucketType
import com.wit.iris.elastic.aggregations.types.interfaces.Bucket

class GlobalAgg extends AggregationType implements Bucket{

    String type = BucketType.GLOBAL.getValue()

    @Override
    void createAggregationMap(){
        this.aggregationMap = [(type) : [:]]
    }
}