package com.wit.iris.elastic.aggregations.types.operation

import grails.validation.Validateable

class Order implements Validateable{

    String attributeName
    String order
    Map orderMap

    //object will return a map in the following format
    // order : {$attributeName : $order}
    static constraints = {
        order(inList : ["desc","asc"])
        orderMap(nullable: true)
    }

    @Override
    String toString(){
        return this.orderMap.toString()
    }

    void createOrderMap(){
        this.orderMap = ["order":[(this.attributeName) : this.order]]
    }
}
