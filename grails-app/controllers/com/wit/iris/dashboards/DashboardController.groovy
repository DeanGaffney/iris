package com.wit.iris.dashboards

import com.wit.iris.revisions.Revision
import grails.converters.JSON

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
        List<Dashboard> dashboards = dashboardService.getDashboards()
        println 'called index'
        println dashboards
        render(view: "index", model:[dashboards: dashboards])
    }

    def save(){
        dashboardService.save(request.JSON)
        redirect(view: "index")
    }

    def update(){
        Dashboard updatedDashboard = dashboardService.update(request.JSON)
        List<Revision> revisions = dashboardService.getRevisions(updatedDashboard.revision.revisionId)
        revisions.removeIf{rev -> rev.revisionNumber == updatedDashboard.revision.revisionNumber}
        render(template: "show", model: [dashboard: updatedDashboard, serializedData: updatedDashboard.grid.serializedData, revisions: revisions, revisionNumber: updatedDashboard.revision.revisionNumber])
    }

    def create(){
        render(template: "create")
    }

    /**
     * Archives a dashboard
     * @param id - the id of the dashboard to archive
     * @return redirect to the index view
     */
    def delete(){
        String revisionId = request.JSON.dashboardRevisionId as String
        long revisionNumber = request.JSON.dashboardRevisionNumber as Long
        boolean shouldReload = request.JSON.reload as Boolean

        Revision revision = dashboardService.getRevision(revisionId, revisionNumber)

        Dashboard dashboard = dashboardService.getDashboard(revision)
        dashboard.archived = true
        dashboard.isRendering = false
        dashboard.save(flush: true)

        revision.archived = true
        revision.save(flush: true)

        if(!shouldReload){
            Dashboard latestDashboard = dashboardService.getLatestDashboard(revisionId)
            List<Revision> revisions = dashboardService.getRevisions(latestDashboard.revision.revisionId)
            revisions.removeIf{rev -> rev.revisionNumber == latestDashboard.revision.revisionNumber}
            render(template: "show", model: [dashboard: latestDashboard, serializedData: latestDashboard.grid.serializedData, revisions: revisions, revisionNumber: latestDashboard.revision.revisionNumber])
            return
        }
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
        List<Revision> revisions = dashboardService.getRevisions(revisionId)
        Revision rev = dashboardService.getRevision(revisions, revisionNumber)
        Dashboard dashboard = dashboardService.getDashboard(rev)
        dashboard.setIsRendering(true)                  //set the dashboard as rendering
        dashboard.save(flush: true)
        revisions.removeIf{it.revisionNumber == dashboard.revision.revisionNumber}
        render(template: "show", model: [dashboard: dashboard, serializedData: dashboard.grid.serializedData, revisions: revisions, revisionNumber: dashboard.revision.revisionNumber])
    }

    /**
     * Called when a user closes down a dashboard they were previously viewing
     * The dashboard is then marked as not being rendered
     * @param id - the id of the dashboard
     * @return redirects to index page
     */
    def onShowViewClosing(){
        Revision rev = dashboardService.getRevision(request.JSON.dashboardRevisionId as String, request.JSON.dashboardRevisionNumber as Long)
        Dashboard dashboard = dashboardService.getDashboard(rev)
        dashboard.isRendering = false
        Map resp = [status: 500, message: "failed to toggle dashboard rendering state"]

        if(dashboard.save(flush: true)){
            resp.status = 200
            resp.message = "successfully toggled dashboard rendering state"
        }
        render resp as JSON
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

    /**
     * Changes the current revision's rendering state and sends down the request revisions serialized data
     * to base the new revision off of
     * @return
     */
    def onRevisionChange(){
        String revisionId = request.JSON.dashboardRevisionId as String
        long revisionNumber = request.JSON.dashboardRevisionNumber as Long
        long requestedRevisionNumber = request.JSON.requestedRevisionNumber as Long

        List<Revision> revisions = dashboardService.getRevisions(revisionId)

        Revision legacyRevision = dashboardService.getRevision(revisions, revisionNumber)
        Dashboard legacyDashboard = dashboardService.getDashboard(legacyRevision)
        legacyDashboard.isRendering = false
        legacyDashboard.save()

        Revision currentRevision = dashboardService.getRevision(revisions, requestedRevisionNumber)
        Dashboard dashboard = dashboardService.getDashboard(currentRevision)
        dashboard.isRendering = true                  //set the dashboard as rendering
        dashboard.save(flush: true)
        revisions.removeIf{rev -> rev.revisionNumber == dashboard.revision.revisionNumber}
        render(template: "show", model: [dashboard: dashboard, serializedData: dashboard.grid.serializedData, revisions: revisions, revisionNumber: dashboard.revision.revisionNumber])
    }

}
