package com.wit.iris.elastic

import com.wit.iris.elastic.aggregations.AggregationObject
import com.wit.iris.elastic.aggregations.types.buckets.Terms
import com.wit.iris.elastic.aggregations.types.interfaces.Metric
import grails.gorm.transactions.Transactional

@Transactional
class AggregationBuilderService {

    /**
     * Takes an aggregation from a list, and a previous existing aggregation, and combines their
     * maps to create a nested map
     * @param currentAggregation, the aggregation object selected by the current index in buildAggregation()
     * @param nextAggregation, the aggregation object selected by the next index in buildAggregation()
     * {@link #buildAggregation(ArrayList<com.wit.iris.elastic.aggregations.AggregationObject>) buildAggregation} method.
     * @return nextAggregation, which now contains the currentAggregation aggregation map within it's own map (nested)
     */
    AggregationObject nestAggregation(AggregationObject currentAggregation, AggregationObject nextAggregation){
        //nextAgg agg map should nest the current aggregation inside itself
        Map nestedMap = nextAggregation.aggMap["aggregations"][nextAggregation.name] + currentAggregation.aggMap
        //get correct ordering for the map
        nestedMap = getEmbeddedOrderMap(currentAggregation,nextAggregation, nestedMap)
        //create the root aggregation with nested maps
        Map nextMap = [(nextAggregation.name) : nestedMap]
        nextAggregation.aggMap = ["aggregations" : nextMap]
        return nextAggregation
    }

    /**
     * @param currentAggregation, the aggregation object selected by the current index in buildAggregation(), sent from nestAggregation()
     * @param nextAggregation, the aggregation object selected by the next index in buildAggregation(), sent from nestAggregation()
     * @param nestedMap, a map containing currentAggregations' aggMap nested inside nextAggregations aggMap eg. ([nextAgg:[currentAgg]])
     * {@link #nestAggregation(AggregationObject, AggregationObject) nestAggregation} method.
     * {@link #buildAggregation(ArrayList<com.wit.iris.elastic.aggregations.AggregationObject>) buildAggregation} method.
     * @return nestMap, the original nestedMap with the correct order statement
     */
    Map getEmbeddedOrderMap(AggregationObject currentAggregation, AggregationObject nextAggregation, Map nestedMap){
        //if the order exists in this map
        if(nestedMap[nextAggregation.aggType.type]["order"]){
            String key,value
            //get the key and value pair
            nestedMap[nextAggregation.aggType.type]["order"].each {k,v -> key = k; value = v}
            //if the current aggregation is a metric and embedded is the key
            if(currentAggregation.aggType instanceof Metric && key == "embedded") {
                //order by the nested aggregation i.e currentAggregation.name
                nestedMap[nextAggregation.aggType.type]["order"] = [(currentAggregation.name): value]
            }
            //no way to sort terms by embedded buvket aggs currently. Leaving else block here in case it gets supported. As of version 5.5 this code will throw and exception
            else if(currentAggregation.aggType instanceof Terms && key == "embedded") {
                nestedMap[nextAggregation.aggType.type]["order"] = [(currentAggregation.name) : value]
            }
            else  if(key == "embedded"){
                nestedMap[nextAggregation.aggType.type]["order"] = [(currentAggregation.name) : value]
            }
            /*
            else{
                //default to order to '_term' if no order suits
                nestedMap[nextAggregation.aggType.type]["order"] = ["_term" : value]
            } */
        }
        return nestedMap
    }

    /**
     * @param aggregations, a list of aggregation objects to be built into a tree structure
     * {@link #buildAggregation(ArrayList<com.wit.iris.elastic.aggregations.AggregationObject>) buildAggregation} method.
     * @return an aggregation object with an aggregtionMap containing the full ES aggregation
     */
    AggregationObject getAggregation(ArrayList<AggregationObject> aggregations){
        //if list size is 1 just return that, else return result from builtAggregation()
        AggregationObject agg = (aggregations.size() == 1) ? aggregations[0] : buildAggregation(aggregations)
        //add this to map to avoid sending back documents,we only want results
        agg.aggMap["size"] = 0
        return agg
    }

    /**
     * <p>
     *   Loops over a list of aggregations and uses the nest method to
     * nest all aggregations together, resulting in the first element
     * being the main query which contains all the aggregations nested into
     * one main aggregation object
     * @param aggregations, a list of com.wit.iris.elastic.aggregations.AggregationObject objects
     * @return agg, com.wit.iris.elastic.aggregations.AggregationObject object containing the full com.wit.iris.elastic.aggregations.AggregationObject query as it's aggMap
     */
    AggregationObject buildAggregation(ArrayList<AggregationObject> aggregations){
        AggregationObject agg
        for (int i = aggregations.size() - 1; i >=1; i--) {
            agg = aggregations[i-1] = nestAggregation(aggregations[i],aggregations[i-1])
        }
        return agg
    }

    AggregationObject buildBasicChartAggregation(AggregationObject aggregation, String aggOrderName, String orderBy, int limit){
        aggregation.aggMap["aggregations"][aggregation.name][aggregation.aggType.type]["order"] = [(aggOrderName): orderBy]
        aggregation.aggMap["aggregations"][aggregation.name][aggregation.aggType.type]["size"] = limit
        return aggregation
    }

}