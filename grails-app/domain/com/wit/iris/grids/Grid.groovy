package com.wit.iris.grids

class Grid {

    String serializedData        //JSON from gridstack.js

    static mapping = {
        serializedData sqlType: 'text'
    }

    static constraints = {
        serializedData(nullable: false)
    }
}
