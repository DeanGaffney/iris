package com.wit.iris.rules.executors

import com.wit.iris.rules.Rule
import com.wit.iris.schemas.IrisSchema
import com.wit.iris.users.User
import grails.testing.gorm.DataTest
import org.grails.testing.GrailsUnitTest
import org.grails.web.json.JSONObject
import spock.lang.Specification

class RuleExecutorSpec extends Specification implements GrailsUnitTest, DataTest {

    IrisSchema schema
    Rule rule
    User user
    JSONObject json

    @Override
    Class[] getDomainClassesToMock() {
        return [User, IrisSchema, Rule]
    }

    def setup() {
        user = new User(username: "deangaffney", password: "password")
        rule = new Rule(script: "json.avg = json.total / json.size \n" +
                                "return json")
        schema = new IrisSchema(name: "Performance Monitor", esIndex: "performance_monitor",
                refreshInterval: 10000, rule: rule, user: user)
        json = new JSONObject(["total": 6, "size": 2])
    }

    def cleanup() {
    }

    void "test execute method"() {
        when: "I execute the schema rule"
        Map data = RuleExecutor.execute(schema.rule, json as Map)

        then: "the map returned has a new field called average"
        assert data.avg != null
        assert data.avg == 3
    }
}