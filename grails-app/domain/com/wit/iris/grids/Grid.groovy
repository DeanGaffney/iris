package com.wit.iris.grids

import com.wit.iris.charts.Chart

class Grid {

    String serializedData        //JSON from gridstack.js

    static hasMany = [charts: Chart]

    static mapping = {
        serializedData sqlType: 'text'
        charts cascade: 'all-delete-orphan'
    }

    static constraints = {
        serializedData(nullable: false)
    }
}
