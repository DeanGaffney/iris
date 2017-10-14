package com.wit.iris.schemas

class Schema {

    String name
    String esIndex
    Long refreshInterval

    static hasMany = [schemaFields: SchemaField]

    static mapping = {
        schemaFields cascade: 'all-delete-orphan'
    }

    static constraints = {
        name(nullable: false, blank: false, maxSize: 100, unique: true, matches: "^[\\w\\s]+\$")
        esIndex(nullable: false, blank: false, unique: true, matches: "^[\\w]+\$")
        refreshInterval(nullable: false)
    }
    
}
