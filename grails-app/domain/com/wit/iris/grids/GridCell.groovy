package com.wit.iris.grids

import com.wit.iris.charts.Chart

class GridCell {

    Integer gridPosition

    static belongsTo = [grid: Grid]
    static hasOne = [chart: Chart]

    static constraints = {
        chart(nullable: false, unique: true)
        gridPosition(nullable: false, min: 0)
    }
}
