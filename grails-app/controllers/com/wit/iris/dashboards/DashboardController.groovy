package com.wit.iris.dashboards

class DashboardController {

    static scaffold = Dashboard

    def dashboardService

    def index(){
        List<Dashboard> dashboards = Dashboard.list()
        render(view: "index", model:[dashboards: dashboards])
    }

    def save(){
        dashboardService.save(request.JSON)
        redirect(view: "index")
    }

    def create(){
        render(template: "create")
    }

    def show(long id){
        Dashboard dashboard = Dashboard.get(id)
        render(template: "show", model: [dashboard: dashboard, serializedData: dashboard.grid.serializedData])
    }

}
