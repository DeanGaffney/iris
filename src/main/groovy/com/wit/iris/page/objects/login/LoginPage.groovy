package com.wit.iris.page.objects.login

import com.codeborne.selenide.Condition
import com.wit.iris.page.objects.Page
import com.wit.iris.page.objects.Wait
import static com.codeborne.selenide.Selenide.$


/**
 * Created by dean on 12/12/17.
 */
class LoginPage extends Page{

    private static final String USERNAME_FIELD = "#username"
    private static final String PASSWORD_FIELD = "#password"
    private static final String SUBMIT_BUTTON = "#submit"

    LoginPage(){
        super(USERNAME_FIELD, Condition.visible, Wait.SHORT)
    }

    void login(){

        //enter username
        $(USERNAME_FIELD).waitUntil(Condition.visible, Wait.SHORT.getTime()).setValue("admin")

        //enter password
        $(PASSWORD_FIELD).waitUntil(Condition.visible, Wait.SHORT.getTime()).setValue("password")

        //click login
        $(SUBMIT_BUTTON).click()
    }
}
