package com.wit.iris.revisions

class Revision {

    String comment
    String revisionId = UUID.randomUUID().toString().toUpperCase()
    long revisionNumber = 0
    Date dateCreated
    boolean archived = false

    static constraints = {
        revisionId(nullable: false)
        revisionNumber(nullable: false)
        comment(nullable: true)
        archived(nullbale: true)
    }

    static mapping = {
        comment sqlType: 'text'
        sort revisionNumber: "desc"
    }
}
