package com.wit.iris.dashboards

import groovy.json.JsonOutput

class DashboardController {

    static scaffold = Dashboard

    def dashboardService

    def save(){
        Dashboard dashboard = dashboardService.save(request.JSON)
        redirect(view: "index")
    }

    def create(){
        render(template: "create")
    }

}
