package com.wit.iris.functional.pages.schemas

import com.codeborne.selenide.CollectionCondition
import com.codeborne.selenide.Condition
import com.wit.iris.functional.pages.Page
import com.wit.iris.schemas.SchemaField

import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.SelenideElement
import com.wit.iris.functional.pages.Wait
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$$


/**
 * Created by dean on 12/12/17.
 */
class SchemaPage extends Page {

    private static final String CREATE_BUTTON = "#create-schema-btn"

    private static final String SCHEMA_CONTAINER = "#create-schema-container"
    private static final String SCHEMA_NAME_FIELD = "#schema-name"
    private static final String SCHEMA_REFRESH_FIELD = "#schema-refresh"
    private static final String ADD_SCHEMA_FIELD_BUTTON = "#add-schema-field-btn"
    private static final String SAVE_SCHEMA_BUTTON = "#save-schema-btn"

    private static final String SCHEMA_FIELD_NAME_FIELD = "#schema-field-name"
    private static final String SCHEMA_FIELD_TYPE_FIELD = "#schema-field-type"
    private static final String SCHEMA_FIELD_CONFIRM_BUTTON = "#schema-field-confirm-btn"

    public static final String SCHEMA_ROWS = ".schema-row"
    private static final String SCHEMA_LINKS = ".schema-li-name"
    private static final String DELETE_SCHEMA_BUTTON = "#delete-schema-btn"

    private numOfSchemas

    SchemaPage(){
        super(CREATE_BUTTON, visible, Wait.SHORT)
        this.numOfSchemas = getNumberOfSchemas()
    }

    SchemaPage createSchema(String schemaName, List<SchemaField> schemaFields){
        $(CREATE_BUTTON).waitUntil(visible, Wait.SHORT.getTime()).click()

        SelenideElement schemaContainer = $(SCHEMA_CONTAINER).waitUntil(visible, Wait.SHORT.getTime())

        schemaContainer.find(SCHEMA_NAME_FIELD).setValue(schemaName)

        schemaContainer.find(SCHEMA_REFRESH_FIELD).setValue(Integer.toString(Wait.SHORT.getTime()))

        schemaFields.each {field -> addSchemaField(field.name, field.fieldType)}

        schemaContainer.find(SAVE_SCHEMA_BUTTON).click()

        //wait for save to complete
        $$(SCHEMA_ROWS).waitUntil(CollectionCondition.size(getSchemaCountProperty() + 1), Wait.MEDIUM.getTime())

        setSchemaCountProperty( getSchemaCountProperty() + 1)


        return this
    }

    SchemaPage deleteSchema(String schemaName){
        SelenideElement schema = getSchema(schemaName)
        schema.click()

        int numOfSchemas = getNumberOfSchemas()
        $(DELETE_SCHEMA_BUTTON).waitUntil(visible, Wait.SHORT.getTime()).click()

        $$(SCHEMA_ROWS).waitUntil(CollectionCondition.size(numOfSchemas - 1), Wait.MEDIUM.getTime())

        setSchemaCountProperty(numOfSchemas - 1)

        return this
    }

    SelenideElement getSchema(String schemaName){
        return $$(SCHEMA_LINKS).find(Condition.text(schemaName))
    }

    private void addSchemaField(String fieldName, String fieldType){
        $(ADD_SCHEMA_FIELD_BUTTON).click()

        //input field attributes
        $(SCHEMA_FIELD_NAME_FIELD).waitUntil(visible, Wait.SHORT.getTime()).setValue(fieldName)
        $(SCHEMA_FIELD_TYPE_FIELD).waitUntil(visible, Wait.SHORT.getTime()).selectOption(fieldType)

        //add the field
        $(SCHEMA_FIELD_CONFIRM_BUTTON).click()
    }

    int getNumberOfSchemas(){
        return $$(SCHEMA_ROWS).size()
    }

    int getSchemaCountProperty(){
        return this.numOfSchemas
    }

    void setSchemaCountProperty(int count){
        this.numOfSchemas = count
    }

}
