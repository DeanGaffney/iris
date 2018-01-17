package com.wit.iris.dashboards

import com.wit.iris.charts.Chart
import com.wit.iris.elastic.Aggregation
import com.wit.iris.grids.Grid
import com.wit.iris.schemas.Schema
import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput
import org.grails.web.json.JSONObject

@Transactional
class DashboardService {

    def springSecurityService
    def aggregationService

    Dashboard save(JSONObject dashboardJson){
        log.debug("Dashboard save request:\n${JsonOutput.prettyPrint(dashboardJson.toString())}")

        Dashboard dashboard = new Dashboard(name: dashboardJson["name"].toString(),
                                            grid: new Grid(serializedData: dashboardJson["grid"].toString()))

        dashboard.user = springSecurityService.getCurrentUser()

        createCharts(dashboardJson["grid"])

        if(!(dashboard.validate() && dashboard.save(flush: true))){
            log.debug(dashboard.errors.allErrors*.toString())
        }else{
            //TODO throw an exception
        }
        return dashboard
    }

    void createCharts(JSONObject gridJson){
        gridJson["serializedData"].each{ele ->
            Schema schema = Schema.get(ele["schemaId"] as Long)

            Aggregation agg = new Aggregation(esIndex: schema.esIndex, json: ele["aggregation"].toString())
            agg.levels = aggregationService.countAggregationLevels(agg.json.toString())

            Chart chart = new Chart(name: ele["chartName"], chartType: ele["chartType"],
                                    aggregation: agg, schema: schema)
            if(!(chart.validate() && chart.save(flush: true))){
                log.debug(chart.errors.allErrors*.toString())
            }else{
                //TODO throw an exception
            }
        }
    }


}
