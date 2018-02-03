package com.wit.iris.routing

import com.wit.iris.rules.executors.RuleExecutor
import com.wit.iris.schemas.IrisSchema
import com.wit.iris.schemas.enums.IrisSchemaField
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject
import grails.plugins.rest.client.RestResponse


@Transactional
class RouteService {

    def elasticService

    /**
     * Routes incoming data to Elasticsearch
     **/
    RestResponse route(IrisSchema schema, JSONObject json) {
        Map data = json as Map
        if(schema.rule != null){
            data = RuleExecutor.execute(schema.rule, data)
        }
        data[IrisSchemaField.INSERTION_DATE.getFieldName()] = new Date().getTime()                  //add insertion date to data
        log.debug("Data for Schema[${schema.id}]:\n ${data.toString()}")
        return elasticService.insert(schema.esIndex, data)
    }
}
