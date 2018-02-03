package com.wit.iris.grids

import com.wit.iris.charts.Chart
import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import com.wit.iris.schemas.IrisSchema
import com.wit.iris.users.User
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class GridCellSpec extends Specification {

    User user
    Grid grid
    GridCell gridCell
    Chart chart
    IrisSchema schema
    Aggregation aggregation

    def setupData(){
        user = new User(username: "deangaffney", password: "password")
        schema = new IrisSchema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 1000)
        aggregation = new Aggregation(esIndex: schema.esIndex, json: "{}")
        chart = new Chart(name: "SQL Chart", chartType: ChartType.BAR.getValue(),
                aggregation: aggregation, schema: schema)
        grid = new Grid(gridCellPositions: "[{some: json}]")
        gridCell = new GridCell(gridPosition: 0, chart: chart)
        grid.addToGridCells(gridCell)

        user.addToSchemas(schema)
        user.save(flush: true)

        grid.save(flush: true, failOnError: true)


        assert User.count() == 1
        assert Grid.count() == 1
        assert GridCell.count() == 1
        assert Chart.count() == 1
        assert IrisSchema.count() == 1
        assert Aggregation.count() == 1
    }

    def setup() {
    }

    def cleanup() {
    }

    void "test deleting grid cell doesn't delete dashboard.chart"(){
        setup:
        setupData()

        when: "I delete a grid cell"
        grid.removeFromGridCells(gridCell)

        and: "I save the update"
        grid.save(flush: true)

        then: "the dashboard.chart still exists"
        assert GridCell.count() == 0
        assert Chart.count() == 1
    }

    void "test update cascade of Chart on GridCell save"(){
        setup:
        setupData()

        when: "I edit a dashboard.chart"
        chart.chartType = ChartType.BUBBLE.getValue()

        and: "I save the grid cell"
        gridCell.save(flush: true)

        then: "the grid cell update is saved too"
        assert Chart.findByChartType("Bubble") != null
    }

}
