package com.wit.iris.webdrivers

import com.codeborne.selenide.WebDriverRunner
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.DesiredCapabilities
import com.codeborne.selenide.Configuration

class DriverFactory {

    static final String GECKO_ENV_NAME = "GECKO_DRIVER"

    static void setFirefoxDriver(){
        Configuration.browser = "gecko"
        DesiredCapabilities caps = DesiredCapabilities.firefox()
        caps.setCapability("marionette", true)
        System.setProperty("webdriver.gecko.driver", System.getenv(GECKO_ENV_NAME))
        WebDriverRunner.setWebDriver(new FirefoxDriver((caps)))
    }

}
