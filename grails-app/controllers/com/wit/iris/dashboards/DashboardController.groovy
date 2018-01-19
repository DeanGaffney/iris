package com.wit.iris.dashboards

class DashboardController {

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

    def delete(long id){
        Dashboard dashboard = Dashboard.get(id)
        dashboard.archived = true
        dashboard.isRendering = false
        dashboard.save(flush: true)
        redirect(view: "index")
    }

    def show(long id){
        Dashboard dashboard = Dashboard.get(id)
        dashboard.setIsRendering(true)                  //set the dashboard as rendering
        dashboard.save(flush: true)
        render(template: "show", model: [dashboard: dashboard, serializedData: dashboard.grid.serializedData])
    }

    def onShowViewClosed(long id){
        Dashboard dashboard = Dashboard.get(id)
        dashboard.setIsRendering(false)
        Map resp = [status: 500, message: "failed to toggle dashboard rendering state"]
        if(dashboard.save(flush: true)){
            resp.status = 200
            resp.message = "successfully toggled dashboard rendering state"
        }
        redirect(view: "index")
    }

}
