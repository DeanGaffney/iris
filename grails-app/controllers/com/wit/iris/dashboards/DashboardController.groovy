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
        dashboard.save(flush: true)
        redirect(view: "index")
    }

    def show(long id){
        Dashboard dashboard = Dashboard.get(id)
        render(template: "show", model: [dashboard: dashboard, serializedData: dashboard.grid.serializedData])
    }

}
