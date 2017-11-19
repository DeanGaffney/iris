package com.wit.iris.charts

import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import com.wit.iris.elastic.ElasticEndpoint
import com.wit.iris.elastic.ElasticService
import com.wit.iris.schemas.Schema
import com.wit.iris.users.User
import grails.plugins.rest.client.RestResponse
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class ChartServiceSpec extends Specification {

    ChartService chartService
    ElasticService elasticService

    ElasticEndpoint elasticEndpoint
    String shakespeareIndex
    Aggregation agg
    RestResponse resp

    Chart chart
    Schema schema
    User user

    def setupData(){
        elasticEndpoint = new ElasticEndpoint(name: "aws", url: "https://search-iris-ibwkuxcv4b2unly3c7d2d77v2a.eu-west-1.es.amazonaws.com", active: true)
        elasticEndpoint.save(flush: true)
        shakespeareIndex = "shakespeare"
        user = new User(username: "dean", password: "password")
        schema = new Schema(name: "test", esIndex: "test", refreshInterval: 1000, user: user)
        user.addToSchemas(schema).save(flush: true)

        assert Schema.count() == 1
        assert User.count() == 1
    }

    def setup(){

    }

    def cleanup() {
    }

    void "test formatting data for bar chart with aggregation level 1"(){
        setup:
        setupData()
        agg = new Aggregation(esIndex: shakespeareIndex, json: '{"aggs":{"agg1":{"terms":{"field": "play_name"}}},"size": 0}',
                levels: 1)
        chart = new Chart(chartType: ChartType.BAR.getValue(),
                name: "sql chart", aggregation: agg, schema: schema)


        when: "I execute an aggregation"
        resp = elasticService.executeAggregation(agg)
        assert resp.statusCodeValue == 200

        and: "I format the data"
        Map chartData = chartService.formatChartData(chart, resp.json)

        then:
        assert chartData == [data:[columns:[['Hamlet', 4244], ['Coriolanus', 3992],
                                             ['Cymbeline', 3958], ['Antony and Cleopatra', 3862], ['Henry VI Part 2', 3334], ['Henry IV', 3205],
                                             ['Henry VI Part 3', 3138], ['Alls well that ends well', 3083], ['Henry V', 3077], ['Henry VI Part 1', 2983]]]]
    }

    void "test formatting data for bar chart with aggregation level 2"() {
        setup:
        setupData()
        agg = new Aggregation(esIndex: shakespeareIndex, json: '{"aggs":{"agg1":{"terms":{"field": "play_name"},"aggs":{"agg2":{"sum":{"field" : "speech_number"}}}}},"size": 0}',
                levels: 2)
        chart = new Chart(chartType: ChartType.BAR.getValue(),
                name: "sql chart", aggregation: agg, schema: schema)

        when: "I execute an aggregation"
        resp = elasticService.executeAggregation(agg)
        assert resp.statusCodeValue == 200

        and :" I format the data"
        Map chartData = chartService.formatChartData(chart, resp.json)

        then:
        assert chartData == [data:[columns:[['Hamlet', 198107.0], ['Coriolanus', 135568.0],
                                            ['Cymbeline', 130105.0], ['Antony and Cleopatra', 112274.0], ['Henry VI Part 2', 90021.0], ['Henry IV', 111754.0],
                                            ['Henry VI Part 3', 73898.0], ['Alls well that ends well', 106670.0], ['Henry V', 60763.0], ['Henry VI Part 1', 49013.0]]]]
    }
}
