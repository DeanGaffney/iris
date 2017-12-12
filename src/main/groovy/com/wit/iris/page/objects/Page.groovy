package com.wit.iris.page.objects

import com.codeborne.selenide.Condition
import static com.codeborne.selenide.Selenide.$

/**
 * Created by dean on 12/12/17.
 */
class Page {

    Page(String identifier, Condition condition, Wait waitType){
        assert identify(identifier, condition, waitType)
    }

    public boolean identify(String cssSelector, Condition condition, Wait waitType){
        return $(cssSelector).waitUntil(condition, waitType.getTime()).exists()
    }
}
