package com.wit.iris.functional.pages.navbar

import com.codeborne.selenide.Condition
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.SelenideElement
import com.wit.iris.functional.pages.aggregations.AggregationPage
import com.wit.iris.functional.pages.dashboards.DashboardPage
import com.wit.iris.functional.pages.schemas.SchemaPage
import static com.codeborne.selenide.Selenide.$$

class Navbar{

    private static final String NAV_ITEMS = ".nav-item > a"

    private static final String SCHEMA_NAV_ITEM_TEXT = "Schemas"
    private static final String AGGREGATION_NAV_ITEM_TEXT = "Aggregations"
    private static final String DASHBOARD_NAV_ITEM_TEXT = "Dashboards"

    SchemaPage goToSchemaPage(){
        //click the schema link
        getNavItem(SCHEMA_NAV_ITEM_TEXT).click()
        return new SchemaPage()
    }

    AggregationPage goToAggregationPage(){
        getNavItem(AGGREGATION_NAV_ITEM_TEXT).click()
        return new AggregationPage()
    }

    DashboardPage goToDashboardPage(){
        getNavItem(DASHBOARD_NAV_ITEM_TEXT)
        return new DashboardPage()
    }

    private ElementsCollection getNavItems(){
        return $$(NAV_ITEMS)
    }

    private SelenideElement getNavItem(String navItemName){
        return getNavItems().find(Condition.text(navItemName))
    }

}