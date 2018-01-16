package com.wit.iris.elastic

class Aggregation {

    String esIndex
    String json
    int levels       //the total number of aggregations in this agg, including root

    static mapping = {
        json sqlType: 'text'
    }

    static constraints = {
        esIndex(nullable: false, blank: false)
        json(nullable: false, blank: false)
        levels(nullable: false)
    }
}
