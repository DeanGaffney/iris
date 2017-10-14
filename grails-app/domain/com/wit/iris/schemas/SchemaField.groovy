package com.wit.iris.schemas;

import com.wit.iris.schemas.enums.FieldType;

class SchemaField {

    String name
    String fieldType

    static belongsTo = [schema: Schema]

    static constraints = {
        name(nullable: false, matches: "^\\w+\$", blank: false)
        fieldType(nullable: false, inList: FieldType.values()*.getValue())
    }
}
