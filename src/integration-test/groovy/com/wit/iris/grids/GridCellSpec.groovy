package com.wit.iris.grids

import com.wit.iris.charts.Chart
import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import com.wit.iris.schemas.Schema
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class GridCellSpec extends Specification {

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
        grid.save(flush: true, failOnError: true)

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

    void "test deleting grid cell doesn't delete chart"(){
        setup:
        setupData()

        when: "I delete a grid cell"
        grid.removeFromGridCells(gridCell)

        and: "I save the update"
        grid.save(flush: true)

        then: "the chart still exists"
        assert GridCell.count() == 0
        assert Chart.count() == 1
    }

    void "test update cascade of Chart on GridCell save"(){
        setup:
        setupData()

        when: "I edit a chart"
        chart.chartType = ChartType.BUBBLE.getValue()

        and: "I save the grid cell"
        gridCell.save(flush: true)

        then: "the grid cell update is saved too"
        assert Chart.findByChartType("Bubble") != null
    }

}
