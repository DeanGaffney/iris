package com.wit.iris.dashboards

class DashboardController {

    static scaffold = Dashboard

    def save(){
        println request.JSON
        def dashboard
        render(template: "show", model: [dashboard: dashboard])
    }

    def create(){
        render(template: "create")
    }

}
