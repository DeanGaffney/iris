package com.wit.iris.functional.pages.login

import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selenide.$
import com.wit.iris.functional.pages.Page
import com.wit.iris.functional.pages.Wait


/**
 * Created by dean on 12/12/17.
 */
class LoginPage extends Page{

    private static final String USERNAME_FIELD = "#username"
    private static final String PASSWORD_FIELD = "#password"
    private static final String SUBMIT_BUTTON = "#submit"

    LoginPage(){
        super(USERNAME_FIELD, visible, Wait.SHORT)
    }

    LoginPage login(){

        //enter username
        $(USERNAME_FIELD).waitUntil(visible, Wait.SHORT.getTime()).setValue("admin")

        //enter password
        $(PASSWORD_FIELD).waitUntil(visible, Wait.SHORT.getTime()).setValue("password")

        //click login
        $(SUBMIT_BUTTON).click()

        //the login page redirects to the page with all controller links, but this will do for now
        return this
    }
}
