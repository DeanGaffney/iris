package com.wit.iris.enums
/**
 * Created by dean on 09/10/17.
 */

enum FieldType {
    INT("int"),
    LONG("long"),
    DOUBLE("double"),
    FLOAT("float"),
    BOOLEAN("boolean"),
    STRING("String"),
    DATE("Date");

    private final String value;

    private FieldType(String value){
        this.value = value;
    }

    String getValue(){ return this.value; }
}