package com.wit.iris.rest

import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse

@Transactional
class RestService {

    RestBuilder rest = new RestBuilder()
    RestResponse resp

    int put(String url, String jsonData){
        resp = rest.put(url){
            contentType "application/json"
            json jsonData
        }
        logResponse()
        return resp.statusCodeValue
    }

    int delete(String url){
        resp = rest.delete(url)
        logResponse()
        return resp.statusCodeValue
    }

    void logResponse(){
        println("Status code : $resp.statusCodeValue")
        println("Json response: ${resp.json.toString()}")
    }
}
