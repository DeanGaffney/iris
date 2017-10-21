package com.wit.iris.elastic.aggregations.types.range

class DateElementRange {

    Date to
    Date from

    Map createRangeObject(){
        Map rangeObject = [:]
        if(from){
            rangeObject.put("from",from.getTime())
        }
        if(to){
            rangeObject.put("to",to.getTime())
        }
        return rangeObject
    }

}
