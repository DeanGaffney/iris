package com.wit.iris.page.objects.schemas

import static com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.SelenideElement
import com.wit.iris.page.objects.Wait
import static com.codeborne.selenide.Selenide.$


/**
 * Created by dean on 12/12/17.
 */
class SchemaPage {

    private static final String CREATE_BUTTON = "#create-schema-btn"

    private static final String SCHEMA_CONTAINER = "#create-schema-container"
    private static final String SCHEMA_NAME_FIELD = "#schema-name"
    private static final String SCHEMA_REFRESH_FIELD = "#schema-refresh"
    private static final String ADD_SCHEMA_FIELD_BUTTON = "#add-schema-field-btn"
    private static final String SAVE_SCHEMA_BUTTON = "#save-schema-btn"

    private static final String SCHEMA_FIELD_CONTAINER = "#schema-field-container"
    private static final String SCHEMA_FIELD_NAME_FIELD = ".schema-field-name"
    private static final String SCHEMA_FIELD_TYPE_FIELD = ".schema-field-type"

    SchemaPage(){
        super(CREATE_BUTTON, visible, Wait.SHORT)
    }

    void createSchema(){
        $(CREATE_BUTTON).waitUntil(visible, Wait.SHORT.getTime()).click()

        SelenideElement schemaContainer = $(SCHEMA_CONTAINER).waitUntil(visible, Wait.SHORT.getTime())

        schemaContainer.find(SCHEMA_NAME_FIELD).setValue("test-schema")

        schemaContainer.find(SCHEMA_REFRESH_FIELD).setValue(Wait.SHORT.getTime())

        addSchemaField(schemaContainer)

        schemaContainer.find(SAVE_SCHEMA_BUTTON).click()
    }

    void addSchemaField(SelenideElement schemaContainer, String fieldName, String fieldType){
        schemaContainer.find(ADD_SCHEMA_FIELD_BUTTON).click()

        SelenideElement schemaField = $(SCHEMA_FIELD_CONTAINER).waitUntil(visible, Wait.SHORT.getTime())
        schemaField.find(SCHEMA_FIELD_NAME_FIELD).setValue("test-field")
        schemaField.find(SCHEMA_FIELD_TYPE_FIELD).selectOption("integer")
    }

    void deleteSchema(){

    }
}
