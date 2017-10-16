package com.wit.iris.schemas

import com.wit.iris.schemas.enums.FieldType

class SchemaFieldController {

    static scaffold = SchemaField

    def form(){
        render(template: 'form', model: [index: params.index, fieldTypes: FieldType.values()*.getValue()])
    }

}
