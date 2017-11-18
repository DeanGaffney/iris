package com.wit.iris.rest

import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.json.JsonOutput
import org.grails.web.json.JSONObject

@Transactional
class RestService {

    RestBuilder rest = new RestBuilder()
    RestResponse resp

    /**
     * REST PUT method, this method converts a map to a json string
     * @param url - the url to act on
     * @param jsonData - the json data in Map format to send to the url
     * @return response from the endpoint
     */
    JSONObject put(String url, Map jsonData){
        resp = rest.put(url){
            contentType "application/json"
            json JsonOutput.toJson(jsonData)
        }
        logResponse()
        return resp.json
    }

    /**
     * REST PUT method
     * @param url - the url to act on
     * @param jsonData - the json data in string format to send to the url
     * @return response from the endpoint
     */
    JSONObject put(String url, String jsonData){
        resp = rest.put(url){
            contentType "application/json"
            json jsonData
        }
        logResponse()
        return resp.json
    }

    /**
     * REST POST method
     * @param url - url to act on
     * @param jsonData - the json data in string format to send to the url
     * @return response from the endpoint
     */
    JSONObject post(String url, String jsonData){
        resp = rest.post(url){
            contentType "application/json"
            json jsonData
        }
        logResponse()
        return resp.json
    }

    /**
     * REST DELETE method
     * @param url - the url to act on
     * @return response from the endpoint
     */
    JSONObject delete(String url){
        resp = rest.delete(url)
        logResponse()
        return resp.json
    }

    /**
     * Logs out the status code and json from the current response object
     */
    void logResponse(){
        log.debug("Status code : $resp.statusCodeValue\n Json response: ${resp.json.toString()}")
    }
}
