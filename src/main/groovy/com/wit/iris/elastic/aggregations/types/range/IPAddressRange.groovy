package com.wit.iris.elastic.aggregations.types.range

import grails.validation.Validateable
import org.apache.commons.validator.routines.InetAddressValidator

class IPAddressRange implements Validateable{

    String to
    String from

    //need to check that at least one field is a valid IP address
    static constraints = {
        to(nullable: true, validator: {return (it ? InetAddressValidator.getInstance().isValid(it) : true)})
        from(nullable: true, validator: {return (it ? InetAddressValidator.getInstance().isValid(it) : true)})
    }

    Map createRangeObject(){
        Map rangeObject = [:]
        if(from) {
            rangeObject.put("from", from)
        }
        if(to){
            rangeObject.put("to",to)
        }
        return rangeObject
    }
}
