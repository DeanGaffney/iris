package com.wit.iris.grids

import com.wit.iris.charts.Chart

class GridCell {

    Integer gridPosition //TODO dont think i need this, gridstack will have this stored
    Chart chart

    static belongsTo = [grid: Grid]

    static constraints = {
        chart(nullable: false, unique: true)
        gridPosition(nullable: false, min: 0)
    }
}
