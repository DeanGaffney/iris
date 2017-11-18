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
        agg = new Aggregation(esIndex: shakespeareIndex, json: "{\n" +
                "\t\"aggs\":{\n" +
                "\t\t\"agg1\":{\n" +
                "\t\t\t\t\"terms\":{\"field\": \"play_name\"},\n" +
                "\t\t\t\t\"aggs\":{\n" +
                "\t\t\t\t\t\"agg2\":{\n" +
                "\t\t\t\t\t\t\"sum\":{\"field\" : \"speech_number\"}\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t}\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"size\": 0\n" +
                "}" , levels: 2)
        chart = new Chart(chartType: ChartType.BAR.getValue(), name: "sql chart", aggregation: agg, schema: schema)
        user.addToSchemas(schema).save(flush: true)

        assert Schema.count() == 1
        assert User.count() == 1
    }

    def setup(){

    }

    def cleanup() {
    }

    void "test formatting data for bar chart"() {
        setup:
        setupData()

        when: "I execute an aggregation"
        resp = elasticService.executeAggregation(agg)
        assert resp.statusCodeValue == 200

        and :" I format the data"
        chartService.formatChartData(chart, resp.json)

        then:
        assert true
    }
}
