package com.wit.iris.grids

import com.wit.iris.charts.Chart
import com.wit.iris.charts.enums.ChartType

import com.wit.iris.elastic.Aggregation
import com.wit.iris.schemas.Schema
import com.wit.iris.users.User
import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class GridSpec extends Specification implements DomainUnitTest<Grid>, DataTest{

    User user
    Grid grid
    GridCell gridCell
    Chart chart
    Schema schema
    Aggregation aggregation

    @Override
    Class[] getDomainClassesToMock() {
        return [User, Grid, GridCell, Chart, Schema, Aggregation]
    }

    def setupData(){
        user = new User(username: "deangaffney", password: "password")
        schema = new Schema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 1000)
        aggregation = new Aggregation(esIndex: schema.esIndex, json: "{}", levels: 1)
        chart = new Chart(name: "SQL Chart", chartType: ChartType.BAR.getValue(),
                aggregation: aggregation, schema: schema)
        grid = new Grid(gridCellPositions: "[{some: json}]")
        gridCell = new GridCell(gridPosition: 0, chart: chart)
        grid.addToGridCells(gridCell)
        grid.save(flush: true, failOnError: true)

        user.addToSchemas(schema)
        user.save(flush: true)

        assert User.count() == 1
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


    void "test delete Grid"(){
        setup:
        setupData()

        when: "I delete the grid"
        grid.delete(flush: true)

        then: "Grid count is 0"
        assert Grid.count() == 0
    }

    void "test Grid gridPositions constraints"(){
        setup:
        setupData()

        when: "I change a gridCellPositions to be null"
        grid.gridCellPositions = null

        then: "It is not valid"
        !grid.validate()
    }

}
