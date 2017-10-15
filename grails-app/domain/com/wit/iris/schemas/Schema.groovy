package com.wit.iris.schemas

import com.wit.iris.users.User

class Schema {

    String name
    String esIndex
    Long refreshInterval

    static hasMany = [schemaFields: SchemaField]
    static belongsTo = [user: User]

    static mapping = {
        schemaFields cascade: 'all-delete-orphan'
    }

    static constraints = {
        name(nullable: false, blank: false, maxSize: 100, unique: true, matches: "^[\\w\\s]+\$")
        esIndex(nullable: false, blank: false, unique: true, matches: "^[\\w]+\$")
        refreshInterval(nullable: false)
    }
    
}
