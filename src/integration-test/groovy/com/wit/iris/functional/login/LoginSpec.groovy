package com.wit.iris.functional.login

import grails.testing.mixin.integration.Integration
import grails.transaction.*

import com.wit.iris.functional.FunctionalSpec
import com.wit.iris.functional.pages.login.LoginPage

@Integration
@Rollback
class LoginSpec extends FunctionalSpec {

    def setup() {
    }

    def cleanup() {
    }

    void "test login"() {
        expect:
        assert new LoginPage().login()
    }

}
