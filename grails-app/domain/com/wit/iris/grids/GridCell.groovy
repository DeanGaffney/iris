package com.wit.iris.grids

import com.wit.iris.charts.Chart

class GridCell {

    Chart chart
    Integer gridPosition

    static belongsTo = [grid: Grid]

    static constraints = {
        chart(nullable: false)
        gridPosition(nullable: false, min: 0)
    }
}
