package com.wit.iris.dashboards

import com.wit.iris.grids.Grid

class Dashboard {

    String name
    Grid grid

    static constraints = {
        name(nullable: false, blank: false)
    }
}
