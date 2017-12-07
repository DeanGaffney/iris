package com.wit.iris.users

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification
import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open

@Integration
@Rollback
class LoginSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test login"() {
        expect:
        assert login()
    }

    private boolean login(){

        open("http://localhost:9000/")

        //find login controller link and click it
        $("li > a[href='/login/index']").click()

        //enter username
        $("#username").waitUntil(visible).setValue("admin")

        //enter password
        $("#password").waitUntil(visible).setValue("password")

        //click login
        $("#submit").click()

        return $("li > a[href='/schema/index']").exists()
    }
}
