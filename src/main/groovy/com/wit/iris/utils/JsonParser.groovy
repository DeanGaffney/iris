package com.wit.iris.utils

import groovy.json.JsonSlurper
import org.grails.web.json.JSONObject

/**
 * Created by dean on 21/10/17.
 */
class JsonParser extends JsonSlurper{

    Object parse(JSONObject json){
        return parseText(json.toString())
    }
}
