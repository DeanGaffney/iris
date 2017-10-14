package com.wit.iris.charts

import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import com.wit.iris.schemas.Schema
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class ChartSpec extends Specification {

    Chart chart
    Schema schema
    Aggregation aggregation

    def setupData(){
        schema = new Schema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 1000)
        schema.save(flush: true)
        aggregation = new Aggregation(esIndex: schema.esIndex, json: "{}")
        chart = new Chart(name: "SQL CHART", chartType: ChartType.BUBBLE.getValue(), aggregation: aggregation)
        chart.save(flush: true)

        assert Chart.count() == 1
        assert Aggregation.count() == 1
        assert Schema.count() == 1
    }
    def setup() {
    }

    def cleanup() {
    }

    void "test deleting a Chart doesn't delete an aggregation"() {
        setup:
        setupData()

        when: "I delete a chart"
        chart.delete(flush: true)

        then:
        Chart.count() == 0
        Aggregation.count() == 1
    }
}