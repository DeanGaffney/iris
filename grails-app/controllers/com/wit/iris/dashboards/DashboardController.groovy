package com.wit.iris.dashboards

import com.wit.iris.revisions.Revision
import grails.converters.JSON
import org.grails.datastore.mapping.query.api.BuildableCriteria

class DashboardController {

    //this is a test aggregation for testing out charts on the dashboard against the node_agent

//    {
//        "size": 0,
//        "aggs": {
//        "aggs_1": {
//            "terms": {
//                "field": "osName"
//            },
//            "aggs": {
//                "aggs_2": {
//                    "max": {
//                        "field": "memUsed"
//                    }
//                }
//            }
//        }
//      }
//    }

    static scaffold = Dashboard

    def dashboardService

    def index(){
        def results = Dashboard.executeQuery("select distinct d.revision.revisionId, MAX(d.revision.revisionNumber) from Dashboard d " +
                "group by d.revision.revisionId")
        List<Dashboard> dashboards = results.collect{ list ->
            Revision rev = Revision.findWhere(revisionId: list[0], revisionNumber: list[1])
            Dashboard.findWhere([revision: rev, archived: false])
        }

        render(view: "index", model:[dashboards: dashboards])
    }

    def save(){
        dashboardService.save(request.JSON)
        redirect(view: "index")
    }

    def update(){
        Dashboard updatedDashboard = dashboardService.update(request.JSON)
        List<Revision> revisions = Revision.findAllWhere([revisionId: updatedDashboard.revision.revisionId])
        render(template: "show", model: [dashboard: updatedDashboard, serializedData: updatedDashboard.grid.serializedData, revisions: revisions])
    }

    def create(){
        render(template: "create")
    }

    /**
     * Archives a dashboard
     * @param id - the id of the dashboard to archive
     * @return redirect to the index view
     */
    def delete(String revisionId, long revisionNumber){
        Revision rev = Revision.findWhere([revisionId: revisionId, revisionNumber: revisionNumber])
        Dashboard dashboard = Dashboard.findWhere([revision: rev])
        dashboard.archived = true
        dashboard.isRendering = false
        dashboard.save(flush: true)
        redirect(view: "index")
    }

    /**
     * Called when a user wishes to view a dashboard
     * The dashboard is then marked as rendering
     * @param revisionId - the revisionId of the dashboard to view
     * @param revisionNumber - the revisionNumber of the dashboard to view
     * @return show template with the dashboard and serialized json for dashboard grid
     */
    def show(String revisionId, long revisionNumber){
        List<Revision> revisions = Revision.findAllWhere([revisionId: revisionId])
        Revision rev = revisions.find {rev -> rev.revisionNumber == revisionNumber}
        Dashboard dashboard = Dashboard.findWhere([revision: rev])
        dashboard.setIsRendering(true)                  //set the dashboard as rendering
        dashboard.save(flush: true)
        render(template: "show", model: [dashboard: dashboard, serializedData: dashboard.grid.serializedData, revisions: revisions])
    }

    /**
     * Called when a user closes down a dashboard they were previously viewing
     * The dashboard is then marked as not being rendered
     * @param id - the id of the dashboard
     * @return redirects to index page
     */
    def onShowViewClosing(){
        Revision rev = Revision.findWhere([revisionId: request.JSON.dashboardRevisionId as String, revisionNumber: request.JSON.dashboardRevisionNumber as Long])
        Dashboard dashboard = Dashboard.findWhere([revision: rev])
        dashboard.setIsRendering(false)
        Map resp = [status: 500, message: "failed to toggle dashboard rendering state"]

        if(dashboard.save(flush: true)){
            resp.status = 200
            resp.message = "successfully toggled dashboard rendering state"
        }
        redirect(view: "index")
    }

    def onShowViewClosed(){
        Dashboard.withSession {
            it.flush()
            it.clear()
        }
        render [:] as JSON
    }

    /**
     * Executes charts aggregation upon loading
     * @return response as json
     */
    def onDashboardChartLoad(){
        dashboardService.onDashboardChartsLoad(request.JSON.dashboardRevisionId as String, request.JSON.dashboardRevisionNumber as Long)
        Map resp = [status: 200, message: "Charts updated"]
        render resp as JSON
    }

    def onRevisionChange(){
        String revisionId = request.JSON.dashboardRevisionId as String
        long revisionNumber = request.JSON.dashboardRevisionNumber as Long
        long requestedRevisionNumber = request.JSON.requestedRevisionNumber as Long

        List<Revision> revisions = Revision.findAllWhere([revisionId: revisionId])

        Revision legacyRevision = revisions.find { rev -> rev.revisionNumber == revisionNumber}
        Dashboard legacyDashboard = Dashboard.findWhere([revision: legacyRevision])
        legacyDashboard.setIsRendering(false)
        legacyDashboard.save()

        Revision currentRevision = revisions.find {rev -> rev.revisionNumber == requestedRevisionNumber}
        Dashboard dashboard = Dashboard.findWhere([revision: currentRevision])
        dashboard.setIsRendering(true)                  //set the dashboard as rendering
        dashboard.save(flush: true)
        render(template: "show", model: [dashboard: dashboard, serializedData: dashboard.grid.serializedData, revisions: revisions])
    }

}
