package com.wit.iris.dashboards

import com.wit.iris.grids.Grid
import com.wit.iris.revisions.Revision
import com.wit.iris.users.User

class Dashboard {

    String name
    Grid grid
    boolean archived = false
    boolean isRendering = false
    Revision revision

    static belongsTo = [user: User]

    static constraints = {
        name(nullable: false, blank: false)
        grid(nullable: false)
        archived(nullable: true)
        isRendering(nullable: true)
        revision(nullable: false)
    }

}
