package com.wit.iris.functional

import com.wit.iris.functional.pages.login.LoginPage
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification
import com.wit.iris.functional.webdrivers.DriverFactory
import com.codeborne.selenide.WebDriverRunner
import static com.codeborne.selenide.Selenide.open
import com.wit.iris.functional.pages.Page

@Integration
@Rollback
class FunctionalSpec extends Specification {

    def setup() {
        DriverFactory.setFirefoxDriver()
        //Page.getNavbar().goToLoginPage().login()
    }

    def cleanup() {
        WebDriverRunner.getWebDriver().close()
    }

}
