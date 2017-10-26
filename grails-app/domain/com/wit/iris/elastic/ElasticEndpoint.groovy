package com.wit.iris.elastic

class ElasticEndpoint {

    String name
    String url
    boolean active

    static constraints = {
        name(nullable: false, blank: false)
        url(nullable: false, url: true)
        active(nullable: false)
    }
}
