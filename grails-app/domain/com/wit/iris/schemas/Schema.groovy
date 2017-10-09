package com.wit.iris.schemas

class Schema {

    String name;

    static hasMany = [schemaFields: SchemaField];

    static constraints = {
        schemaFields(nullable: true);
    }
    
}
