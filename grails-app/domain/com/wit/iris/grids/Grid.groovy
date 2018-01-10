package com.wit.iris.grids

class Grid {

    String gridCellPositions        //JSON from gridstack.js
    static hasMany = [gridCells: GridCell]

    static mapping = {
        gridCells cascade: 'all-delete-orphan'
    }

    static constraints = {
        gridCellPositions(nullable: false)
    }
}
