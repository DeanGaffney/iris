package com.wit.iris.schemas

import com.wit.iris.rules.Rule
import com.wit.iris.users.User

class Schema {

    String name
    String esIndex
    Long refreshInterval
    Rule rule
    List schemaFields
    boolean archived = false

    static hasMany = [schemaFields: SchemaField]
    static belongsTo = [user: User]

    static mapping = {
        schemaFields cascade: 'all-delete-orphan'
    }

    static constraints = {
        name(nullable: false, blank: false, maxSize: 100, unique: true, matches: "^[\\w\\s]+\$")
        esIndex(nullable: false, blank: false, unique: true, matches: "^[\\w]+\$")
        refreshInterval(nullable: false)
        rule(nullable: true)
        archived(nullable: true)
    }
    
}
