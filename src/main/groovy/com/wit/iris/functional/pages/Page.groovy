package com.wit.iris.functional.pages

import com.codeborne.selenide.Condition
import static com.codeborne.selenide.Selenide.$
import com.wit.iris.functional.pages.navbar.Navbar

/**
 * Created by dean on 12/12/17.
 */
class Page {

    protected static final NAVBAR = new Navbar()

    Page(String identifier, Condition condition, Wait waitType){
        assert identify(identifier, condition, waitType)
    }

    boolean identify(String cssSelector, Condition condition, Wait waitType){
        return $(cssSelector).waitUntil(condition, waitType.getTime()).exists()
    }

    protected Navbar getNavbar(){
        return NAVBAR
    }
}
