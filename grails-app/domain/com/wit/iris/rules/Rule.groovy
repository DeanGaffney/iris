package com.wit.iris.rules

class Rule {

    String script

    static constraints = {
        script(nullable: false, blank: false)
    }
}
