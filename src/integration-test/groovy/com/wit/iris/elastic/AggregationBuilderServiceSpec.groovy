package com.wit.iris.elastic

import com.wit.iris.elastic.aggregations.AggregationObject
import com.wit.iris.elastic.aggregations.types.buckets.Terms
import com.wit.iris.elastic.aggregations.types.metric.Average
import com.wit.iris.elastic.aggregations.types.metric.Sum
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class AggregationBuilderServiceSpec extends Specification {

    AggregationBuilderService aggregationBuilderService
    Average avg
    Terms terms
    Sum sum
    AggregationObject aggAvg
    AggregationObject aggTerms
    AggregationObject aggSum

    List<AggregationObject> aggs

    def setup() {
        avg = new Average(field: "grade", dataType: "integer")
        terms = new Terms(field: "name", dataType: "String")
        sum = new Sum(field: "score", dataType: "double")

        aggAvg = new AggregationObject(name: "agg1", aggType: avg)
        aggAvg.createAggregationMap()
        aggTerms = new AggregationObject(name: "agg2", aggType: terms)
        aggTerms.createAggregationMap()
        aggSum = new AggregationObject(name: "agg3", aggType: sum)
        aggSum.createAggregationMap()

        aggs = [aggAvg, aggSum, aggTerms]
    }

    def cleanup() {
    }

    void "test aggregation builder with 1 aggregation"(){
        when: "I get an aggregation from the builder service"
        AggregationObject agg = aggregationBuilderService.getAggregation(aggs.subList(0, 1))

        then: "The aggregation map will look like the following"
        agg.toString() == '{"aggregations":{"agg1":{"avg":{"field":"grade"}}},"size":0}'
    }

    void "test aggregation builder with 2 aggregations"(){
        when: "I get an aggregation from the builder service"
        AggregationObject agg = aggregationBuilderService.getAggregation(aggs.subList(0, 2))

        then: "The aggregation map will look like the following"
        agg.toString() == '{"aggregations":{"agg1":{"avg":{"field":"grade"},"aggregations":{"agg3":{"sum":{"field":"score"}}}}},"size":0}'
    }

    void "test creating Aggregation domain from builder service"(){
        when: "I get an aggregation from the builder service"
        AggregationObject agg = aggregationBuilderService.getAggregation(aggs.subList(0, 1))

        and: " I create an aggregation domain from the service"
        Aggregation aggregation = new Aggregation(esIndex: "some/index", json: agg.toString())

        then: "The aggregation is valid"
        assert aggregation.validate()
    }

}
