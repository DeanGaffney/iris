package com.wit.iris.users

import com.wit.iris.charts.Chart
import com.wit.iris.charts.enums.ChartType
import com.wit.iris.dashboards.Dashboard
import com.wit.iris.elastic.Aggregation
import com.wit.iris.grids.Grid
import com.wit.iris.grids.GridCell
import com.wit.iris.schemas.Schema
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class UserSpec extends Specification {

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
        chart = new Chart(name: "SQL Chart", chartType: ChartType.BAR.getValue(),
                aggregation: aggregation, schema: schema)
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

    void "test editing user"(){
        setup:
        setupData()

        when: "I change the users username"
        user.username = "DeanGaffney"

        and: "I save the user"
        user.save(flush: true)

        then: "I can find the user by the new username"
        assert User.findByUsername("DeanGaffney") != null
    }

    void "test deleting the user"(){
        setup:
        setupData()

        when: "I delete the user"
        user.delete(flush: true)

        then: "The user no longer exists"
        assert User.count() == 0
    }

    void "test cascade delete of Schema on user"(){
        setup:
        setupData()

        when: "I remove a schema from the users collection"
        List schemaCharts = Chart.findAllWhere(schema: schema)
        println schemaCharts.toString()
        
        Chart.executeUpdate("DELETE Chart as c WHERE c.schema.id =:schemaId", [schemaId: schema.id])

        GridCell.executeUpdate("DELETE GridCell as g WHERE g.chart in (:charts)", [charts: schemaCharts])
        user.removeFromSchemas(schema)

        and: "I save the user"
        user.save(flush: true)

        then: "The schema is deleted"
        Schema.count() == 0
    }

    void "test cascade delete of Dashboard on user"(){
        setup:
        setupData()

        when: "I remove a dashboard from the users collection"
        user.removeFromDashboards(dashboard)

        and: "I save the user"
        user.save(flush: true)

        then: "The dashboard is deleted"
        Dashboard.count() == 0
    }

}
