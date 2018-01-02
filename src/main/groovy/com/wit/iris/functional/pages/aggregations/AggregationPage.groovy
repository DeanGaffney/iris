package com.wit.iris.functional.pages.aggregations

import com.codeborne.selenide.CollectionCondition
import com.codeborne.selenide.Condition
import com.wit.iris.elastic.aggregations.types.interfaces.AggType
import com.wit.iris.functional.pages.Page
import com.wit.iris.functional.pages.Wait

import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$$

class AggregationPage extends Page{

    private static final String CREATE_AGG_BUTTON = "#create-agg-btn"
    private static final String SCHEMA_SELECT = "#agg-schema-select"
    private static final String AGG_FIELD_SELECT = "#agg-field-select"
    private static final String ADD_AGG_BUTTON = "#add-agg-btn"
    private static final String EXECUTE_AGG_BUTTON = "#execute-agg-btn"
    private static final String AGG_RESULT_CONTAINER = "#agg-result-container"
    private static final String ADDED_AGGS = ".agg-item"

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


    int getAggLevel(){
        return $$(ADDED_AGGS).size()
    }

    void addAggregation(){

    }

    AggregationPage createTermsAggregation(){


        return this
    }

    AggregationPage executeAggregation(){
        //execute aggregation
        $(EXECUTE_AGG_BUTTON).click()

        //wait for result to display
        $(AGG_RESULT_CONTAINER).waitUntil(Condition.not(Condition.empty), Wait.SHORT.getTime())

        return screenshotAggregationResult()
    }

    AggregationPage screenshotAggregationResult(){
        $(AGG_RESULT_CONTAINER).screenshotAsImage()
        return this
    }
}
