package com.wit.iris.schemas

class Schema {

    String name
    String esIndex
    long refreshInterval

    static hasMany = [schemaFields: SchemaField]

    static mapping = {
        schemaFields cascade: 'all-delete-orphan'
    }

    static constraints = {
        name(nullable: false, blank: false, maxSize: 100)
        esIndex(nullable: true, blank: false)
    }
    
}
