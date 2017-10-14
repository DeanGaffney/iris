package com.wit.iris.com.wit.tests.domains.utils

import com.wit.iris.charts.Chart
import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import com.wit.iris.grids.Grid
import com.wit.iris.grids.GridCell
import com.wit.iris.schemas.Schema
import com.wit.iris.schemas.SchemaField
import com.wit.iris.schemas.enums.FieldType

/**
 * Created by dean on 14/10/17.
 */
class DomainUtils {

    static SchemaField getSchemaField(){
        SchemaField schemaField = new SchemaField(name: "counter", fieldType: FieldType.INT.getValue())
        return schemaField
    }

    static Schema getSchemaWithSingleSchemaField(){
        Schema schema = new Schema(name: "Performance Monitor", esIndex: "performance_monitor", refreshInterval: 10000)
        schema.addToSchemaFields(getSchemaField())
        return schema
    }

    static Grid getGridWithSingleGridCell(){
        Grid grid = new Grid(gridCellPositions: "[{some: json}]")
        grid.addToGridCells(getGridCellWithSingleChart())
        return grid
    }

    static GridCell getGridCellWithSingleChart(){
        GridCell gridCell = new GridCell(gridPosition: 0, chart: getChartWithAggregation(ChartType.BAR.getValue()))
        return gridCell
    }

    static Chart getChartWithAggregation(String chartType){
        Chart chart = new Chart(name: "SQL Chart", chartType: chartType, aggregation: getAggregationWithSchema())
        return chart
    }

    static Aggregation getAggregationWithSchema(){
        Aggregation aggregation = new Aggregation(schema: getSchemaWithSingleSchemaField())
        return aggregation
    }

}
