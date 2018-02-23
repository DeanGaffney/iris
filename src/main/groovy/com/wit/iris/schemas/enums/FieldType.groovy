package com.wit.iris.schemas.enums
/**
 * Created by dean on 09/10/17.
 */

enum FieldType {
    INT("integer"),
    LONG("long"),
    DOUBLE("double"),
    FLOAT("float"),
    BOOLEAN("boolean"),
    STRING("String"),
    DATE("date");

    private final String value;

    private FieldType(String value){
        this.value = value;
    }

    String getValue(){ return this.value; }
}