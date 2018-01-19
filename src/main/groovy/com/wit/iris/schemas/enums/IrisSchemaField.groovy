package com.wit.iris.schemas.enums

/**
 * Created by dean on 19/01/18.
 */
enum IrisSchemaField {

    INSERTION_DATE("insertionDate", FieldType.DATE.getValue());

    private final String fieldName;
    private final String fieldType;

    private IrisSchemaField(String fieldName, String fieldType){
        this.fieldName = fieldName;
        this.fieldType = fieldType
    }

    String getFieldName(){ return this.fieldName; }
    String getFieldType(){ return this.fieldType; }

}