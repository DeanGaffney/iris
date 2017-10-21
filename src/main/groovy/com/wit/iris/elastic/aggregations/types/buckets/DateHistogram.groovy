package com.wit.iris.elastic.aggregations.types.buckets

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.enums.BucketType
import com.wit.iris.elastic.aggregations.types.interfaces.Bucket
import com.wit.iris.elastic.aggregations.types.operation.Order

class DateHistogram extends AggregationType implements Bucket{

    String type = BucketType.DATE_HISTOGRAM.getValue()
    String interval
    String format
    String timeZone
    String offset
    String missing
    Order order

    //uses UTC timezone be default
    //https://www.elastic.co/guide/en/elasticsearch/reference/current/search-aggregations-bucket-datehistogram-aggregation.html

    @Override
    void createAggregationMap(){
        Map operationMap = ["field" : field, "interval": interval]
        if(format){
            operationMap.put("format", format)
        }
        if(timeZone){
            operationMap.put("time_zone" ,timeZone)
        }
        if(offset){
            operationMap.put("offset" ,offset)
        }
        if(missing){
            operationMap.put("missing" ,missing)
        }
        if((order) && order.validate()){
            order.createOrderMap()
            operationMap += order.orderMap
        }
        this.aggregationMap = [(type): operationMap]
    }
}
