package com.wit.iris.elastic.aggregations.types.enums

/**
 * Created by dean on 21/10/17.
 */
enum BucketType {
    DATE_HISTOGRAM("date_histogram"),
    DATE_RANGE("date_range"),
    FILTER("filter"),
    GLOBAL("global"),
    HISTOGRAM("histogram"),
    IP_RANGE("ip_range"),
    MISSING("missing"),
    RANGE("range"),
    SIGNIFICANT_TERMS("significant_terms"),
    TERMS("terms");

    private final String value;

    private BucketType(String value){
        this.value = value;
    }

    String getValue(){ return this.value; }

}