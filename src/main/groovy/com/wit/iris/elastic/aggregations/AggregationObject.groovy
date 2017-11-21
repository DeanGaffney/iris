package com.wit.iris.elastic.aggregations

import com.wit.iris.elastic.aggregations.types.AggregationType
import groovy.json.JsonOutput

/**
 * Created by dean on 21/10/17.
 */
class AggregationObject{

    String name     //the name of the aggregation
    Map aggMap      // the aggregation map
    AggregationType aggType //the type of aggregation type to be put into the aggregation object map

    void createAggregationMap(){
        aggType.createAggregationMap()
        this.aggMap = ["aggregations": [(name): aggType.aggregationMap]]
    }

    @Override
    String toString(){
        return JsonOutput.toJson(aggMap)
    }

    String prettyPrint(){
        return JsonOutput.prettyPrint(JsonOutput.toJson(aggMap))
    }

}
