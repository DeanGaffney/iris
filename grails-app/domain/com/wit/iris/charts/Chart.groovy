package com.wit.iris.charts

import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import com.wit.iris.grids.Grid
import com.wit.iris.schemas.IrisSchema

class Chart {

    String name
    String chartType
    String subscriptionId
    Aggregation aggregation
    IrisSchema schema
    boolean isRaw = false
    boolean archived = false

    static constraints = {
        name(nullable: false, blank: false)
        chartType(nullable: false, inList: ChartType.values()*.getValue())
        aggregation(nullable: false)
        schema(nullable: false)
        archived(nullable: true)
        subscriptionId(nullable: true)
        isRaw(nullable: true)
    }

    static belongsTo = [grid: Grid]
}
