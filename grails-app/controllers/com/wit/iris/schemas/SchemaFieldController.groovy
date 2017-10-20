package com.wit.iris.schemas

class SchemaFieldController {

    static scaffold = SchemaField

    def form(){
        render(template: 'form')
    }

}
