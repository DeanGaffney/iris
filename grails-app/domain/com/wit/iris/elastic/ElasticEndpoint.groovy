package com.wit.iris.elastic

class ElasticEndpoint {

    String name
    String url
    boolean active

    static constraints = {
        name(unique: true, nullable: false, blank: false)
        url(unique: true,  nullable: false, url: true)
        active(nullable: false)
    }
}
