package com.wit.iris.dashboards

class DashboardController {

    static scaffold = Dashboard

    def save(){
        def dashboard
        render(template: "show", model: [dashboard: dashboard])
    }

    def create(){
        render(template: "create")
    }

}
