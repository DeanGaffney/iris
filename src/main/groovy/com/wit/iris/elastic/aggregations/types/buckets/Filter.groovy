package com.wit.iris.elastic.aggregations.types.buckets

import com.wit.iris.elastic.aggregations.types.AggregationType
import com.wit.iris.elastic.aggregations.types.interfaces.Bucket

class Filter extends AggregationType implements Bucket{

    String type = "filter"
    String value        //could make a TERM object and replace value with this composition instead


    //TODO takes a term in its map refactor this to be a TERM object
    @Override
    void createAggregationMap(){
        this.aggregationMap = [(type) : ["term" : [(super.getField()) : value]]]
    }
}
