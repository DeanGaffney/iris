package com.wit.iris.elastic

class Aggregation {

    String esIndex
    String json

    static constraints = {
        esIndex(nullable: false, blank: false)
        json(nullable: false, blank: false)
    }
}
