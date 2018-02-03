package com.wit.iris.schemas;

import com.wit.iris.schemas.enums.FieldType;

class SchemaField implements Comparable {

    String name
    String fieldType

    static belongsTo = [schema: IrisSchema]

    static constraints = {
        name(nullable: false, matches: "^\\w+\$", blank: false)
        fieldType(nullable: false, inList: FieldType.values()*.getValue())
    }

    int compareTo(Object other){
        name <=> other.name
    }
}
