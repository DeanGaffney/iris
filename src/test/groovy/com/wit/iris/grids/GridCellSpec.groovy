package com.wit.iris.grids

import com.wit.iris.charts.Chart
import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import com.wit.iris.schemas.Schema
import com.wit.iris.users.User
import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class GridCellSpec extends Specification implements DomainUnitTest<GridCell>, DataTest{

    User user
    Grid grid
    GridCell gridCell
    Chart chart
    Schema schema
    Aggregation aggregation

    @Override
    Class[] getDomainClassesToMock() {
        return [Grid, GridCell, Chart, Schema, Aggregation]
    }

    def setupData(){
        user = new User(username: "deangaffney", password: "password")
        schema = new Schema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 1000)
        aggregation = new Aggregation(esIndex: schema.esIndex, json: "{}")
        chart = new Chart(name: "SQL Chart", chartType: ChartType.BAR.getValue(),
                aggregation: aggregation, schema: schema)
        grid = new Grid(gridCellPositions: "[{some: json}]")
        gridCell = new GridCell(gridPosition: 0, chart: chart)
        grid.addToGridCells(gridCell)
        grid.save(flush: true, failOnError: true)

        user.addToSchemas(schema)
        user.save(flush: true)

        assert  User.count() == 1
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

    void "test delete GridCell"(){
        setup:
        setupData()

        when: "I delete a grid cell"
        gridCell.delete(flush: true)

        then:
        GridCell.count() == 0
    }

    void "test edit a grid cell"(){
        setup:
        setupData()

        when: "I edit a grid cell"
        gridCell.gridPosition = 1

        and: "I save the change"
        gridCell.save(flush: true)

        then: "I can find the grid cell by its position"
        GridCell.findByGridPosition(1) != null
    }

    void "test gridPosition constraints"(){
        setup:
        setupData()

        when: "I change th grid position to be null"
        gridCell.gridPosition = null

        then: "It is not valid"
        !gridCell.validate()

        when: "I change the grid cell to have negative position"
        gridCell.gridPosition = -1

        then: "It is not valid"
        !gridCell.validate()
    }

}
