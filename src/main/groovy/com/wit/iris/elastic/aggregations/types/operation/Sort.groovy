package com.wit.iris.elastic.aggregations.types.operation

class Sort {

    List<Order> sortList
    Map sortMap

    @Override
    String toString(){
        return this.sortMap.toString()
    }

    /**
     * @return a map of sorting values containing maps of order objects
     */
    void createSortMap(){
        //create each order map before returning map
        this.sortList.each {it.createOrderMap()}
        this.sortMap = ["sort" : sortList]
    }
}
