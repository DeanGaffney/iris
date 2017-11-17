package com.wit.iris.routing

import com.wit.iris.charts.Chart
import com.wit.iris.rules.executors.RuleExecutor
import com.wit.iris.schemas.Schema
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject

@Transactional
class RouteService {

    def elasticService

    /**
     * Routes incoming data to Elasticsearch
     **/
    def route(Schema schema, JSONObject json) {
        Map data = [:]
        if(schema.rule != null){
            data = RuleExecutor.execute(schema.rule, json.fields as Map)
        }else{
            data = json.fields as Map
        }
        elasticService.insert(schema.esIndex, data)
    }
}
