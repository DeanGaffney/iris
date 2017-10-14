package com.wit.iris.grids

import com.wit.iris.charts.Chart
import com.wit.iris.charts.enums.ChartType

import com.wit.iris.elastic.Aggregation
import com.wit.iris.schemas.Schema
import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class GridSpec extends Specification implements DomainUnitTest<Grid>, DataTest{

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
        schema = new Schema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 1000)
        schema.save(flush: true, failOnError: true)
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
