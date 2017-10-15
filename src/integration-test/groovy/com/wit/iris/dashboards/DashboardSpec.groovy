package com.wit.iris.dashboards

import com.wit.iris.charts.Chart
import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import com.wit.iris.grids.Grid
import com.wit.iris.grids.GridCell
import com.wit.iris.schemas.Schema
import com.wit.iris.users.User
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class DashboardSpec extends Specification {

    User user
    Dashboard dashboard
    Grid grid
    GridCell gridCell
    Chart chart
    Schema schema
    Aggregation aggregation

    def setupData(){
        user = new User(username: "deangaffney", password: "password")
        schema = new Schema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 1000)
        aggregation = new Aggregation(esIndex: schema.esIndex, json: "{}")
        chart = new Chart(name: "SQL Chart", chartType: ChartType.BAR.getValue(), aggregation: aggregation)
        grid = new Grid(gridCellPositions: "[{some: json}]")
        gridCell = new GridCell(gridPosition: 0, chart: chart)
        grid.addToGridCells(gridCell)
        dashboard = new Dashboard(name: "JVM Dashboard", grid: grid)

        user.addToSchemas(schema)
        user.addToDashboards(dashboard)
        user.save(flush: true)

        assert User.count() == 1
        assert Dashboard.count() == 1
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
        user.removeFromDashboards(dashboard)

        and: "I save the user"
        user.save(flush: true )

        then: "The grid should still exist"
        assert Dashboard.count() == 0
        assert Grid.count() == 1
    }

    void "test cascade update on Grid on Dashboard save"(){
        setup:
        setupData()

        when: "I edit a Grid"
        grid.gridCellPositions = "{some different json}"

        and: "I save the dashboard"
        dashboard.save(flush: true)

        then: "the update for the grid is saved too"
        assert Grid.findByGridCellPositions("{some different json}") != null
    }


}
