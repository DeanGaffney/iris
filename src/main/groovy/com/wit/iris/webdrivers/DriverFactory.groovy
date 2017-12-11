package com.wit.iris.webdrivers

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.DesiredCapabilities

class DriverFactory {

    static final String FIREFOX = "firefox"
    static final String CHROME = "chrome"


    static WebDriver getDriver(String driverName){
        WebDriver driver = null
        if(driverName == FIREFOX){
            DesiredCapabilities caps = DesiredCapabilities.firefox()
            caps.setCapability("marionette", true)
            driver = new FirefoxDriver(caps)
        }else if(driverName == CHROME){
            System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"))
            System.setProperty("selenide.browser", "Chrome")

            driver = new ChromeDriver()
        }
        return driver
    }

}
