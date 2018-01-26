package com.wit.iris.dashboards

import com.wit.iris.charts.Chart
import com.wit.iris.elastic.Aggregation
import com.wit.iris.grids.Grid
import com.wit.iris.revisions.Revision
import com.wit.iris.schemas.Schema
import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestResponse
import groovy.json.JsonOutput
import org.grails.web.json.JSONObject

@Transactional
class DashboardService {

    def springSecurityService
    def aggregationService
    def chartService

    /**
     * Saves a dashboard using the json request sent to the server
     * A dashboard being saved is given revision number 0
     * @param dashboardJson - the json representing the dashboard
     * @return the saved dashboard
     */
    Dashboard save(JSONObject dashboardJson){
        log.debug("Dashboard save request:\n${JsonOutput.prettyPrint(dashboardJson.toString())}")

        Dashboard dashboard = new Dashboard(name: dashboardJson["name"].toString(),
                                            grid: new Grid(serializedData: dashboardJson["grid"].toString()),
                                            revision: new Revision(comment: "master"))

        dashboard.user = springSecurityService.getCurrentUser()

        createCharts(dashboardJson["grid"], dashboard.grid)

        if(!(dashboard.validate() && dashboard.save(flush: true))){
            log.debug(dashboard.errors.allErrors*.toString())
        }else{
            //TODO throw an exception
        }
        return dashboard
    }

    /**
     * Update a dashboard using JSON request sent from client
     * @param dashboardJson - the json containing the dashboard information
     * @return the updated dashboard
     */
    Dashboard update(JSONObject dashboardJson){
        Revision rev = Revision.findWhere([revisionId: dashboardJson.revisionId as String, revisionNumber: dashboardJson.revisionNumber as Long])
        Dashboard legacyDashboard = Dashboard.findWhere([revision: rev])
        legacyDashboard.setIsRendering(false)
        legacyDashboard.save()

        long revisionNumber = Revision.findAllWhere([revisionId: dashboardJson.revisionId]).asList()*.revisionNumber.max()

        Dashboard currentDashboard = save(dashboardJson)
        currentDashboard.revision = new Revision(revisionId: legacyDashboard.revision.revisionId, revisionNumber: revisionNumber + 1)
        currentDashboard.setIsRendering(true)
        currentDashboard.save(flush: true)
        return currentDashboard
    }



    /**
     * Creates and adds charts to a dashboard grid using grid json information
     * @param gridJson - the json representing the grid containing the chart information
     * @param grid - the grid object to add the charts to
     */
    void createCharts(JSONObject gridJson, Grid grid){
        gridJson["serializedData"].each{ele ->
            Schema schema = Schema.get(ele["schemaId"] as Long)

            Aggregation agg = new Aggregation(esIndex: schema.esIndex, json: ele["aggregation"].toString())
            agg.levels = aggregationService.countAggregationLevels(agg.json.toString())

            Chart chart = new Chart(name: ele["chartName"], chartType: ele["chartType"],
                                    aggregation: agg, schema: schema, grid: grid)

            if(!chart.validate()){
                log.debug(chart.errors.allErrors*.toString())
            }else{
                //TODO throw an exception
            }
            grid.addToCharts(chart)
        }
    }

    /**
     * Returns all dashboards that are currently marked as rendering
     * @return A List of Dashboards that are currently rendering
     */
    List<Dashboard> getRenderingDashboards(){
        List<Dashboard> renderingDashboards = Dashboard.findAllWhere([isRendering: true]).asList()
        log.debug("Found ${renderingDashboards.size()} that are currently rendering")
        return renderingDashboards
    }

    /**
     * Find all charts associated with rendering charts and finds charts which are associated with the
     * schema that has just sent data to the application
     * @param schemaId - schema id of schema that just sent data to the application
     * @return list of charts associated with both the rendering dashboard and the schema that sent data to the application
     */
    List<Chart> getRelevantDashboardCharts(long schemaId){
        List<Chart> relevantCharts = getRenderingDashboards()*.grid*.charts.flatten().findAll{chart -> chart.schema.id == schemaId}
        log.debug("Found ${relevantCharts.size()} charts to update")
        return relevantCharts
    }

    /**
     * Updates relevant dashboard charts
     * @param schemaId - the schema id to associate updates with
     */
    void updateDashboardCharts(long schemaId){
        getRelevantDashboardCharts(schemaId).each {
            //loop over all charts related to schema and execute the aggregation
            RestResponse aggResultData = aggregationService.execute(it.aggregation)
            //update dashboard.chart with aggregation results
            chartService.updateChart(schemaId, it, aggResultData.json)
        }
    }

    /**
     * Called when dashboard is loaded,
     * executes aggregations for charts to give them some data to display
     * this avoids blank charts, as some agents may have long intervals between
     * sending data to the application
     * @param revisionId - the id of the dashboard
     */
    void onDashboardChartsLoad(String revisionId, long revisionNumber){
        Revision rev = Revision.findWhere([revisionId: revisionId, revisionNumber: revisionNumber])
        Dashboard dashboard = Dashboard.findWhere([revision: rev])
        dashboard.grid.charts.each {chart ->
            List<JSONObject> responses = []
            5.times {
                RestResponse aggResultData = aggregationService.execute(chart.aggregation)
                responses.add(aggResultData.json)
            }
            chartService.loadChart(chart.schema.id, chart, responses)
        }
    }

}
