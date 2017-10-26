package com.wit.iris.rest

import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse

@Transactional
class RestService {

    RestBuilder rest = new RestBuilder()
    RestResponse resp

    /**
     * REST PUT method
     * @param url - the url to act on
     * @param jsonData - the json data in string format to send to the url
     * @return status code of the response
     */
    int put(String url, String jsonData){
        resp = rest.put(url){
            contentType "application/json"
            json jsonData
        }
        logResponse()
        return resp.statusCodeValue
    }

    /**
     * REST DELETE method
     * @param url - the url to act on
     * @return status code of the response
     */
    int delete(String url){
        resp = rest.delete(url)
        logResponse()
        return resp.statusCodeValue
    }

    /**
     * Logs out the status code and json from the current response object
     */
    void logResponse(){
        println("Status code : $resp.statusCodeValue")
        println("Json response: ${resp.json.toString()}")
    }
}
