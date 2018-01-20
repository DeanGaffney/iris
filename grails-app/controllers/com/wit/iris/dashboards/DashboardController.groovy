package com.wit.iris.dashboards

import com.wit.iris.charts.enums.ChartType
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
//    }
//    }

    static scaffold = Dashboard

    def dashboardService

    def index(){
        List<Dashboard> dashboards = Dashboard.findAllWhere([archived: false]).asList()
        render(view: "index", model:[dashboards: dashboards])
    }

    def save(){
        dashboardService.save(request.JSON)
        redirect(view: "index")
    }

    def create(){
        render(template: "create")
    }

    /**
     * Archives a dashboard
     * @param id - the id of the dashboard to archive
     * @return redirect to the index view
     */
    def delete(long id){
        Dashboard dashboard = Dashboard.get(id)
        dashboard.archived = true
        dashboard.isRendering = false
        dashboard.save(flush: true)
        redirect(view: "index")
    }

    /**
     * Called when a user wishes to view a dashboard
     * The dashboard is then marked as rendering
     * @param id - the id of the dashboard to view
     * @return show template with the dashboard and serialized json for dashboard grid
     */
    def show(long id){
        Dashboard dashboard = Dashboard.get(id)
        dashboard.setIsRendering(true)                  //set the dashboard as rendering
        dashboard.save(flush: true)
        render(template: "show", model: [dashboard: dashboard, serializedData: dashboard.grid.serializedData])
    }

    /**
     * Called when a user closes down a dashboard they were previously viewing
     * The dashboard is then marked as not being rendered
     * @param id - the id of the dashboard
     * @return redirects to index page
     */
    def onShowViewClosed(long id){
        //if the user clicks the close button on the view the id will not be null,
        //if the user closes or refreshes the tab the id will come from json
        id = (id == null) ? request.JSON.dashboardId : id
        Dashboard dashboard = Dashboard.get(id)
        dashboard.setIsRendering(false)
        Map resp = [status: 500, message: "failed to toggle dashboard rendering state"]
        if(dashboard.save(flush: true)){
            resp.status = 200
            resp.message = "successfully toggled dashboard rendering state"
        }
        redirect(view: "index")
    }

    /**
     * Executes charts aggregation upon loading
     * @return response as json
     */
    def onDashboardChartLoad(){
        dashboardService.onDashboardChartsLoad(request.JSON.dashboardId as Long)
        Map resp = [status: 200, message: "Charts updated"]
        render resp as JSON
    }

}
