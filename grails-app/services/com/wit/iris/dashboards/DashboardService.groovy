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

import java.util.function.Predicate

@Transactional
class DashboardService {

    def springSecurityService
    def aggregationService
    def chartService

    /**
     * Creates a dashboard, with no revision, revisions should be added in the save or update method
     * @param dashboardJson - the json to create the dashboard from
     * @return a dashboard object which needs a revision attached to it
     */
    Dashboard createDashboard(dashboardJson){
        log.debug("Dashboard create request:\n${JsonOutput.prettyPrint(dashboardJson.toString())}")

        Dashboard dashboard = new Dashboard(name: dashboardJson["name"].toString(),
                grid: new Grid(serializedData: dashboardJson["grid"].toString()))

        dashboard.user = springSecurityService.getCurrentUser()

        createCharts(dashboardJson["grid"], dashboard.grid)

        return dashboard
    }

    /**
     * Saves a dashboard using the json request sent to the server
     * A dashboard being saved is given revision number 0
     * @param dashboardJson - the json representing the dashboard
     * @return the saved dashboard
     */
    Dashboard save(JSONObject dashboardJson){

        Dashboard dashboard = createDashboard(dashboardJson)

        dashboard.setRevision(new Revision(comment: "master"))      //default revision with new UID for rev id

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

        long revisionNumber = getHighestRevisionNumber(dashboardJson.revisionId as String)

        Dashboard currentDashboard = createDashboard(dashboardJson)
        currentDashboard.setRevision(new Revision(revisionId: legacyDashboard.revision.revisionId, revisionNumber: revisionNumber + 1))
        currentDashboard.setIsRendering(true)

        if(!(currentDashboard.validate() && currentDashboard.save(flush: true))){
            log.debug(currentDashboard.errors.allErrors*.toString())
        }else{
            //TODO throw an exception
        }
        return currentDashboard
    }

    /**
     * Delets/Archives a dashboard and it's revision
     * @param revisionId - the revision id to get the dashboard with
     * @param revisionNumber - the revision number of the dashboard
     */
    void delete(String revisionId, long revisionNumber){
        Revision revision = getRevision(revisionId, revisionNumber)

        Dashboard dashboard = getDashboard(revision)
        dashboard.archived = true
        dashboard.isRendering = false
        dashboard.save(flush: true)

        revision.archived = true
        revision.save(flush: true)
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
        Revision rev = Revision.findWhere([revisionId: revisionId, revisionNumber: revisionNumber, archived: false])
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

    /**
     * Gets most recent dashboards for each revision and returns them in a  list
     * @return list of dashboards
     */
    List<Dashboard> getDashboards(){
        List<Dashboard> dashboards = []
        List<String> ids = getDistinctRevisionIds()
        ids.each { revId ->
            long highestRevisionNumber = getHighestRevisionNumber(revId)
            Revision rev = getRevision(revId, highestRevisionNumber)
            Dashboard dashboard = Dashboard.findWhere([revision: rev])
            dashboards.add(dashboard)
        }
        return dashboards
    }

    /**
     * Gets a dashboard by revision id and revision number
     * @param revisionId - the revision id
     * @param revisionNumber - the revision number
     * @return dashboard found by using the revision id and revision number
     */
    Dashboard getDashboard(String revisionId, long revisionNumber){
        Revision rev = Revision.findWhere([revisionId: revisionId, revisionNumber: revisionNumber, archived: false])
        Dashboard dashboard = getDashboard(rev)
        return dashboard
    }

    /**
     * Get a dashboard using a revision object
     * @param rev - the revision
     * @return dashboard found by using the revision object
     */
    Dashboard getDashboard(Revision rev){
        return Dashboard.findWhere([revision: rev, archived: false])
    }

    /**
     * Gets the most recent revision of all revisions related to a dashboard
     * @param revisionId - the revision id to compare against
     * @return the latest dashboard
     */
    Dashboard getLatestDashboard(String revisionId){
        return getDashboard(revisionId, getHighestRevisionNumber(revisionId))
    }

    /**
     * Gets all revisions with the same revision id, where the dashboard is not archived
     * @param revisionId - the revision id to use to find the revisions
     * @return a list of revisions that are not related to archived dashboards
     */
    List<Revision> getRevisions(String revisionId){
        return Revision.findAllWhere([revisionId: revisionId, archived: false]).asList()
    }

    /**
     * Get all the unique revision ids
     * @return a list of unique revision ids
     */
    List<String> getDistinctRevisionIds(){
        return Revision.findAllWhere([archived: false]).asList().collect {rev -> rev.revisionId}.toSet().asList()
    }

    /**
     * Gets a revision object by using the revision id and revision number
     * @param revisionId - the revision id
     * @param revisionNumber - the revision number
     * @return a revision object
     */
    Revision getRevision(String revisionId, long revisionNumber){
        return getRevisions(revisionId).find{rev -> rev.revisionNumber == revisionNumber}
    }

    /**
     * Gets a revision object from a list of existing revisions by the revision number
     * @param revisions - the list of revisions to search
     * @param revisionNumber - the revision number to search for
     * @return the revision object found
     */
    Revision getRevision(List<Revision> revisions, long revisionNumber){
        return revisions.find {rev -> rev.revisionNumber == revisionNumber}
    }

    List<Revision> getFilteredRevisions(String revisionId, Predicate<Revision> condition){
        getRevisions(revisionId).removeIf(condition)
    }

    /**
     * Get's the highest revision number for a revisionId
     * @param revisionId - the id to compare revisions against
     * @return the highest revision for the revisionId supplied
     */
    long getHighestRevisionNumber(String revisionId){
        List<Revision> revisions = getRevisions(revisionId)
        List<Long> revisionNumbers = revisions.collect{rev -> rev.revisionNumber}
        return getRevisions(revisionId).collect {rev -> rev.revisionNumber}
                                       .max()
    }

    /**
     * Checks to see if a dashboard has any more revisions
     * @param revisionId - the revisionId to check for revisions against
     * @return true if the highest revisionNumber is greater than 0 AND
     * the highest revision number is within a list of non-archived revisions
     */
    boolean hasOtherRevisions(String revisionId){
        long highestRevNum = getHighestRevisionNumber(revisionId)
        return highestRevNum != 0 &&
               highestRevNum in getRevisions(revisionId)*.revisionNumber
    }

}
