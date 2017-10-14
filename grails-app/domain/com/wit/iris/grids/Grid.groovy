package com.wit.iris.grids

class Grid {

    String gridCellPositions        //JSON from Gridster.js
    static hasMany = [gridCells: GridCell]

    static mapping = {
        gridCells cascade: 'all-delete-orphan'
    }

    static constraints = {
        gridCellPositions(nullable: false)
    }
}
