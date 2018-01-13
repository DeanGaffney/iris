package com.wit.iris.dashboards

class DashboardController {

    static scaffold = Dashboard

    def save(){
        println request.JSON

        render(text: "")
    }

}
