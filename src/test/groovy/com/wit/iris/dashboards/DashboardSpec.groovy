package com.wit.iris.dashboards

import com.wit.iris.charts.Chart
import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import com.wit.iris.grids.Grid
import com.wit.iris.grids.GridCell
import com.wit.iris.schemas.Schema
import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class DashboardSpec extends Specification implements DomainUnitTest<Dashboard>, DataTest{

    Dashboard dashboard
    Grid grid
    GridCell gridCell
    Chart chart
    Schema schema
    Aggregation aggregation

    @Override
    Class[] getDomainClassesToMock() {
        return [Dashboard, Grid, GridCell, Chart, Schema, Aggregation]
    }

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

        assert Dashboard.count() == 1
        assert Grid.count() == 1
        assert GridCell.count() == 1
        assert Chart.count() == 1
        assert Schema.count() == 1
        assert Aggregation.count() == 1
    }

    def cleanup() {
    }

    void "test Dashboard name constraints"(){
        setup:
        setupData()

        when: "I change the name of the dashboard to be null"
        dashboard.name = null

        then: "it is not valid"
        !dashboard.validate()

        when: "I change the name of the dashboard to be blank"
        dashboard.name = ""

        then: "it is not valid"
        !dashboard.validate()
    }

    void "test Dashboard grid constraint"(){
        setup:
        setupData()

        when: "I change the grid to be null"
        dashboard.grid = null

        then: "it is not valid"
        !dashboard.validate()
    }


}
