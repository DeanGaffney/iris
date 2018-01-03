package com.wit.iris.functional.pages.aggregations

import com.codeborne.selenide.CollectionCondition
import com.codeborne.selenide.Condition
import com.codeborne.selenide.SelenideElement
import com.codeborne.selenide.WebDriverRunner
import com.wit.iris.elastic.aggregations.types.interfaces.AggType
import com.wit.iris.functional.pages.Page
import com.wit.iris.functional.pages.Wait
import com.wit.iris.functional.pages.schemas.SchemaPage
import org.openqa.selenium.JavascriptExecutor

import java.util.concurrent.TimeUnit

import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$$
import static com.codeborne.selenide.Selenide.screenshot

class AggregationPage extends Page{

    private static final String CREATE_AGG_BUTTON = "#create-agg-btn"
    private static final String SCHEMA_SELECT = "#agg-schema-select"
    private static final String AGG_FIELD_SELECT = "#agg-field-select"
    private static final String ADD_AGG_BUTTON = "#add-agg-btn"
    private static final String EXECUTE_AGG_BUTTON = "#execute-agg-btn"
    private static final String AGG_RESULT_CONTAINER = "#agg-result-container"
    private static final String ADDED_AGGS = ".agg-item"
    private static final String CLEAR_AGG_BUTTON = "#clear-agg-btn"

    private static final String METRIC_MISSING_INPUT = "#metric-missing-input"
    private static final String AGG_TYPE_POSTFIX = "-btn"

    AggregationPage(){
        super(CREATE_AGG_BUTTON, visible, Wait.SHORT)
    }

    AggregationPage createMetricAggregation(String schemaName, AggType agg, String fieldName, int missingVal){
        //click create
        $(CREATE_AGG_BUTTON).click()

        //choose a schema
        $(SCHEMA_SELECT).waitUntil(visible, Wait.SHORT.getTime()).selectOption(schemaName)

        //click agg type, '#' is for css id
        $("#" + agg.getValue() + AGG_TYPE_POSTFIX).click()

        //select field
        $(AGG_FIELD_SELECT).waitUntil(visible, Wait.SHORT.getTime()).selectOption(fieldName)

        //add missing value
        $(METRIC_MISSING_INPUT).waitUntil(visible, Wait.SHORT.getTime()).setValue(Integer.toString(missingVal))

        int aggLevel = getAggLevel()

        //add the aggregation
        $(ADD_AGG_BUTTON).click()

        //wait for agg to be added to list
        $$(ADDED_AGGS).waitUntil(CollectionCondition.size(aggLevel + 1), Wait.SHORT.getTime())

        return this
    }

    AggregationPage scrollToResult(){
        SelenideElement clearButton = $(CLEAR_AGG_BUTTON).scrollTo()
        JavascriptExecutor js = (JavascriptExecutor)WebDriverRunner.getWebDriver()
        js.executeScript("arguments[0].scrollIntoView();", clearButton)     //scroll to element
        WebDriverRunner.getWebDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)  //wait 2 seconds

        screenshot("agg-result")
        return this
    }

    int getAggLevel(){
        return $$(ADDED_AGGS).size()
    }

    AggregationPage executeAggregation(){
        //execute aggregation
        $(EXECUTE_AGG_BUTTON).click()

        //wait for result to display
        $(AGG_RESULT_CONTAINER).waitUntil(Condition.not(Condition.empty), Wait.SHORT.getTime())

        return this
    }
}
