package com.wit.iris.routing

import com.wit.iris.rules.Rule
import com.wit.iris.schemas.Schema
import com.wit.iris.users.User
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class RouteServiceSpec extends Specification {

    RouteService routeService
    Schema schema
    Rule rule
    User user

    def setup() {
        user = new User(username: "deangaffney", password: "password")
        rule = new Rule(script: "fields.avg = fields.total / fields.size" +
                                "return fields")
        schema = new Schema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 10000)
    }

    def cleanup() {
    }

    void "test "() {

    }
}
