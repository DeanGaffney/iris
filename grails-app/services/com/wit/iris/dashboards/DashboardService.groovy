package com.wit.iris.dashboards

import com.wit.iris.grids.Grid
import com.wit.iris.grids.GridCell
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput

@Transactional
class DashboardService {

    Dashboard save(JSON dashboardJson){
        log.debug("Dashboard save request:\n${JsonOutput.prettyPrint(dashboardJson.toString())}")

        Dashboard dashboard = new Dashboard(name: dashboardJson.name)
        Grid grid = createGrid(dashboardJson.grid)

        dashboard.grid = grid

        if(!(dashboard.validate() && dashboard.save(flush: true))){
            log.debug(dashboard.errors.allErrors*.toString())
        }else{
            //TODO throw an exception
        }
        return dashboard
    }

    Grid createGrid(JSON gridJson){
        Grid grid = new Grid(gridCellPositions: gridJson.gridCellPositions)
        grid.setGridCells(createGridCells(gridJson.gridCells))
        return grid
    }

    List<GridCell> createGridCells(JSON gridCellsJson){

        gridCellsJson.collect {gridCell ->

        }
    }
}
