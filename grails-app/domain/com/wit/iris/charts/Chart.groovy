package com.wit.iris.charts

import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import com.wit.iris.schemas.Schema

class Chart {

    String name
    String chartType
    Aggregation aggregation
    Schema schema

    static constraints = {
        name(nullable: false, blank: false)
        chartType(nullable: false, inList: ChartType.values()*.getValue())
        aggregation(nullable: false)
        schema(nullable: false)
    }
}
