package com.wit.iris.charts

import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject

@Transactional
class ChartService {

    def socketService

    /**
     * Updates a chart with new aggregation result data
     * @param chart - the chart to update
     * @param data - the data to update the chart with
     */
    void updateChart(Chart chart, JSONObject data){
        //check what chart type it is
        Map formattedData = formatChartData(chart, data)
        log.debug("Formatted chart data ${formattedData.toString()}")
        //use socket service to send data to chart
        socketService.sendDataToClient()
    }

    /**
     * Format aggregation result data to suit specified chart
     * @param chart - the chart to format the data for
     * @param data - the aggregation result data
     * @return formatted data for chart
     */
    private Map formatChartData(Chart chart, JSONObject data){

    }
}
