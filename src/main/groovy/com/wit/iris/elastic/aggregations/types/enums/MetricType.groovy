package com.wit.iris.elastic.aggregations.types.enums

/**
 * Created by dean on 21/10/17.
 */
enum MetricType {

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

    private final String value;

    private MetricType(String value){
        this.value = value;
    }

    String getValue(){ return this.value; }

}