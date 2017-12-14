package com.wit.iris.users

import com.codeborne.selenide.WebDriverRunner
import com.wit.iris.page.objects.login.LoginPage
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

import static com.codeborne.selenide.Condition.exist
import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.Configuration
import com.wit.iris.webdrivers.DriverFactory

@Integration
@Rollback
class LoginSpec extends Specification {

    def setup() {
        DriverFactory.setFirefoxDriver()
    }

    def cleanup() {
        WebDriverRunner.getWebDriver().close()
    }

    void "test login"() {
        expect:
        assert login()
    }

    private boolean login(){

        open("http://localhost:9000")

        //find login controller link and click it
        $("li > a[href='/login/index']").waitUntil(visible, 10000).click()

        new LoginPage().login()

        return $("li > a[href='/schema/index']").waitUntil(exist, 10000).exists()
    }
}
