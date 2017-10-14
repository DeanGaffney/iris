package com.wit.iris.dashboards

import com.wit.iris.charts.Chart
import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import com.wit.iris.grids.Grid
import com.wit.iris.grids.GridCell
import com.wit.iris.schemas.Schema
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class DashboardSpec extends Specification {

    Dashboard dashboard
    Grid grid
    GridCell gridCell
    Chart chart
    Schema schema
    Aggregation aggregation

    def setupData(){
        schema = new Schema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 1000)
        schema.save(flush: true)
        aggregation = new Aggregation(esIndex: schema.esIndex, json: "{}")
        chart = new Chart(name: "SQL Chart", chartType: ChartType.BAR.getValue(), aggregation: aggregation)
        grid = new Grid(gridCellPositions: "[{some: json}]")
        gridCell = new GridCell(gridPosition: 0, chart: chart)
        grid.addToGridCells(gridCell)
        dashboard = new Dashboard(name: "JVM Dashboard", grid: grid)
        dashboard.save(flush: true)

        Dashboard.count() == 1
        assert Grid.count() == 1
        assert GridCell.count() == 1
        assert Chart.count() == 1
        assert Schema.count() == 1
        assert Aggregation.count() == 1
    }

    def setup() {
    }

    def cleanup() {
    }

    void "test delete Dashboard"(){
        setup:
        setupData()

        when: "I delete a Dashboard"
        dashboard.delete(flush: true)

        then: "The grid should still exist"
        assert Dashboard.count() == 0
        assert Grid.count() == 1
    }


}
