package com.wit.iris.elastic.aggregations.types

abstract class AggregationType{

    String type     //the type of aggregation (terms, avg etc....)
    String field    //the field to perform the aggregation on
    String dataType // what is the data type of the field
    Map aggregationMap  //the map which builds up the aggregation query

    abstract void createAggregationMap()    //each child must implement their own map aggregation

    //adds keyword to field if data type is string, keyword is needed, without it, the string is tokenized
    String getField(){
        return (dataType == "String") ? this.field + '.keyword' : this.field
    }

}
