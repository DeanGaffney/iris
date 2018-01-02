package com.wit.iris.elastic.aggregations.types.enums

import com.wit.iris.elastic.aggregations.types.interfaces.AggType

/**
 * Created by dean on 21/10/17.
 */
enum MetricType implements AggType{

    AVERAGE("avg"),
    CARDINALITY("cardinality"),
    EXTENDED_STATS("extended_stats"),
    MAX("max"),
    MIN("min"),
    PERCENTILE_RANKS("percentile_ranks"),
    PERCENTILES("percentiles"),
    STATS("stats"),
    SUM("sum"),
    VALUE_COUNT("value_count");

    private final String value

    private MetricType(String value){
        this.value = value
    }

    @Override
    String getValue(){ return this.value }

}