package com.wit.iris.dashboards

import groovy.json.JsonOutput

class DashboardController {

    static scaffold = Dashboard

    def save(){
        def dashboard
        log.debug("Dashboard save request:\n${JsonOutput.prettyPrint(request.JSON.toString())}")
        redirect(view: "index")
    }

    def create(){
        render(template: "create")
    }

}
