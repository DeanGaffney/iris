package com.wit.iris.dashboards

import com.wit.iris.grids.Grid
import com.wit.iris.users.User

class Dashboard {

    String name
    Grid grid
    boolean archived = false
    boolean isRendering = false

    static belongsTo = [user: User]

    static constraints = {
        name(nullable: false, blank: false)
        grid(nullable: false)
        archived(nullable: true)
        isRendering(nullable: true)
    }
}
