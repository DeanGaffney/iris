package com.wit.iris.elastic.aggregations.types.buckets

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.enums.BucketType
import com.wit.iris.elastic.aggregations.types.interfaces.Bucket
import com.wit.iris.elastic.aggregations.types.range.IPAddressRange

class IpRange extends AggregationType implements Bucket{

    String type = BucketType.IP_RANGE.getValue()
    List<IPAddressRange> ranges

    @Override
    void createAggregationMap(){
        Map operationMap = ["field" : super.getField()]
        def rangeList = []
        ranges.each { IPAddressRange range ->
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