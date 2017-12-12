package com.wit.iris.schemas

import com.wit.iris.page.objects.login.LoginPage
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

import static com.codeborne.selenide.Condition.exist
import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open

@Integration
@Rollback
class SchemaFunctionalSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test schema functional"() {

        open("http://localhost:9000")

        //find login controller link and click it
        $("li > a[href='/login/index']").waitUntil(visible, 10000).click()

        new LoginPage().login()

        $("li > a[href='/schema/index']").waitUntil(visible, 10000).click()

        SchemaPage

        assert $("li > a[href='/schema/index']").waitUntil(exist, 10000).exists()
    }
}
