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
class GridSpec extends Specification {

    User user
    Grid grid
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

        user.addToSchemas(schema)
        user.save(flush: true)

        grid.save(flush: true, failOnError: true)

        assert User.count() == 1
        assert Grid.count() == 1
        assert Chart.count() == 1
        assert IrisSchema.count() == 1
        assert Aggregation.count() == 1
    }

    def setup() {

    }

    def cleanup() {
    }

    void "test cascade delete Grid"(){
        setup:
        setupData()

        when: "I delete the grid"
        grid.delete(flush: true)

        then: "Grid cells also get deleted"
        assert Grid.count() == 0
    }

    void "test cascade removeFrom Grid"(){
        setup:
        setupData()

        when: "I delete a gridcell from a grid"

        and: "I save the grid"
        grid.save(flush: true)

        then: "Grid cells also get deleted"
        assert Grid.count() == 1
    }

    void "test cascade update of GridCell on Grid save"(){
        setup:
        setupData()

        when: "I update a grid cell"

        and: "I save the grid"
        grid.save(flush: true)

        then: "The GridCell update is saved too"
    }
}
