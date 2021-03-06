package com.wit.iris.charts

import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import com.wit.iris.schemas.IrisSchema
import com.wit.iris.users.User
import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ChartSpec extends Specification implements DomainUnitTest<Chart>, DataTest{

    User user
    Chart chart
    IrisSchema schema
    Aggregation aggregation

    @Override
    Class[] getDomainClassesToMock() {
        return [User, Chart, IrisSchema, Aggregation]
    }

    def setupData(){
        user = new User(username: "deangaffney", password: "password")
        schema = new IrisSchema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 1000)
        aggregation = new Aggregation(esIndex: schema.esIndex, json: "{}", levels: 1)
        chart = new Chart(name: "SQL CHART", chartType: ChartType.BUBBLE.getValue(),
                aggregation: aggregation, schema: schema)
        user.addToSchemas(schema)
        user.save(flush: true)
        chart.save(flush: true)


        assert User.count() == 1
        assert Chart.count() == 1
        assert Aggregation.count() == 1
        assert IrisSchema.count() == 1
    }

    def setup() {
    }

    def cleanup() {
    }

    void "test archived property is false by default"(){
        setup:
        setupData()

        expect:
        assert !chart.archived
    }

    void "test delete Chart"(){
        setup:
        setupData()

        when: "I delete a dashboard.chart"
        chart.delete(flush: true)

        then: "It is deleted"
        Chart.count() == 0
    }

    void "test name constraints"(){
        setup:
        setupData()

        when: "I change the name to be null"
        chart.name = null

        then: "It is not valid"
        !chart.validate()

        when: "I change the name to be blank"
        chart.name = ""

        then: "it is not valid"
        !chart.validate()
    }

    void "test chartType constraints"(){
        setup:
        setupData()

        when: "I change the chartType to be null"
        chart.chartType = null

        then: "it is not valid"
        !chart.validate()

        when: "I change the chartType to be something outside of the ChartType enum"
        chart.chartType = "INVALID_CHART"

        then: "It is not valid"
        !chart.validate()
    }

    void "test aggregation constraint"(){
        setup:
        setupData()

        when: "I change the aggregation to be null"
        chart.aggregation = null

        then: "It is not valid"
        !chart.validate()
    }

    void "test editing dashboard.chart"(){
        setup:
        setupData()

        when: "I edit the dashboard.chart name"
        chart.name = "New Chart"

        and: "I save the update"
        chart.save(flush: true)

        then: "I can find the dashboard.chart by its new name"
        assert Chart.findByName("New Chart") != null
    }

}
