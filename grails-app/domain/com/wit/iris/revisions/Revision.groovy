package com.wit.iris.revisions

class Revision {

    String comment
    String revisionId = UUID.randomUUID().toString().toUpperCase()
    long revisionNumber = 0
    Date dateCreated

    static constraints = {
        revisionId(nullable: false)
        revisionNumber(nullable: false)
        comment(nullable: true)
    }

    static mapping = {
        sort revisionNumber: "desc"
    }
}
