package com.wit.iris.dashboards

import com.wit.iris.grids.Grid
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class DashboardSpec extends Specification implements DomainUnitTest<Dashboard> {

    Dashboard dashboard
    Grid grid

    def setup() {
        grid = new Grid(gridCellPositions: "[{some: 'json']")
        dashboard = new Dashboard()
    }

    def cleanup() {
    }


}
