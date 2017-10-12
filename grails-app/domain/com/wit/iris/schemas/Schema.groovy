package com.wit.iris.schemas

class Schema {

    String name

    static hasMany = [schemaFields: SchemaField]

    static mapping = {
        schemaFields cascade: 'all-delete-orphan'
    }

    static constraints = {

    }
    
}
