package com.wit.iris.elastic.aggregations.types.enums

import com.wit.iris.elastic.aggregations.types.interfaces.AggType

/**
 * Created by dean on 21/10/17.
 */
enum BucketType implements AggType{

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

    @Override
    String getValue(){ return this.value; }

}