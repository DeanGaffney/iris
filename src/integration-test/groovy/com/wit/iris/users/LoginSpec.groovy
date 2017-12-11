package com.wit.iris.users

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.WebDriverRunner
import com.wit.iris.webdrivers.DriverFactory
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import org.openqa.selenium.chrome.ChromeDriver
import spock.lang.Specification

import static com.codeborne.selenide.Condition.exist
import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open

@Integration
@Rollback
class LoginSpec extends Specification {

    def setup() {
        Configuration.browser = "chrome"
        Configuration.browserSize = "1920x1080"
        Configuration.holdBrowserOpen = true
        System.out.println( System.getenv("CHROME_DRIVER"))
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"))
    }

    def cleanup() {
    }

    void "test login"() {
        expect:
        assert login()
    }

    private boolean login(){

        open("http://localhost:9000")

        //find login controller link and click it
        $("li > a[href='/login/index']").waitUntil(visible, 10000).click()

        //enter username
        $("#username").waitUntil(visible, 10000).setValue("admin")

        //enter password
        $("#password").waitUntil(visible, 10000).setValue("password")

        //click login
        $("#submit").click()

        return $("li > a[href='/schema/index']").waitUntil(exist, 10000).exists()
    }
}
