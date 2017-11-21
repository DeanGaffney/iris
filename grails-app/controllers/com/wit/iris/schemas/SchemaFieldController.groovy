package com.wit.iris.schemas

import com.wit.iris.schemas.enums.FieldType

class SchemaFieldController {

    static scaffold = SchemaField

    def form(){
        render(template: 'form', model: [fieldTypes: FieldType.values()*.getValue()])
    }

    def edit(){
        render(template: "edit", model: [schemaField: schemaField])
    }

}
