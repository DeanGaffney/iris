package com.wit.iris.elastic.aggregations.types.buckets

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.enums.BucketType
import com.wit.iris.elastic.aggregations.types.interfaces.Bucket
import com.wit.iris.elastic.aggregations.types.range.DateElementRange

class DateRange extends AggregationType implements Bucket{

    String type = BucketType.DATE_RANGE.getValue()
    List<DateElementRange> ranges
    String timeZone
    String format


    @Override
    void createAggregationMap(){
        Map operationMap = ["field" : field]

        def rangeList = []
        ranges.each { DateElementRange range ->
            if(range.validate()){
                rangeList.add(range.createRangeObject())
            }
        }
        if(rangeList.size() > 0){
            operationMap.put("ranges",rangeList)
        }
        if(timeZone){
            operationMap.put("time_zone",timeZone)
        }
        if(format){
            operationMap.put("format",format)
        }
        this.aggregationMap = [(type):operationMap]
    }
}