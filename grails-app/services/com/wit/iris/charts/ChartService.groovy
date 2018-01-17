package com.wit.iris.charts

import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONElement
import org.grails.web.json.JSONObject

@Transactional
class ChartService {

    def socketService

    /**
     * Updates a dashboard chart with new aggregation result data
     * @param schemaId - the id of the schema the chart is associated with
     * @param chart - the dashboard.chart to update
     * @param data - the data to update the dashboard.chart with
     */
    void updateChart(long schemaId, Chart chart, JSONObject data){
        //check what dashboard.chart type it is
        Map formattedData = formatChartData(chart, data)
        log.debug("Formatted dashboard.chart data ${formattedData.toString()}")
        //use socket service to send data to dashboard.chart
        socketService.sendDataToClient(schemaId, chart.chartType, formattedData)
    }

    /**
     * Format aggregation result data to suit specified dashboard.chart
     * @param chart - the dashboard.chart to format the data for
     * @param data - the aggregation result data
     * @return formatted data for dashboard.chart
     */
    Map formatChartData(Chart chart, JSONObject data){
        Map formattedData = [:]
        log.debug("Formatting data: \n ${data.toString()}")
        if(chart.chartType == ChartType.BAR.getValue()){
            formattedData = formatDataForBar(data, chart.aggregation)
        }
        return formattedData
    }

    /**
     * Format data for a bar dashboard.chart
     * @param data - the data to format
     * @return A map of formatted data for a bar dashboard.chart
     */
    Map formatDataForBar(JSONObject data, Aggregation agg){
        Map formattedData = [data:[:]]
        log.debug("Formatting data for Bar Chart: \n ${data.toString()}")

        List keys, values
        String option = (agg.levels == 1) ? "doc_count" : "value"
        keys = data.aggregations."aggs_1".buckets*.key
        log.debug("Chart keys: ${keys.toString()}")

        if(agg.levels == 1){
            values = data.aggregations."aggs_1".buckets*."$option"
        }else{
            values = data.aggregations."aggs_1".buckets*."aggs_2"."$option"
        }
        log.debug("Chart values: ${values.toString()}")
        List columns = []   //list of lists
        keys.eachWithIndex{ele, indx -> columns[indx] = [ele,values[indx]]}
        formattedData.data.columns = columns
        log.debug("Formatted data for Chart: ${formattedData.toString()}")
        return formattedData
    }

    //BAR, BUBBLE, PIE - (TERMS), (TERMS, METRIC)

    //GROUP BAR, STACKED ROW - (TERMS, TERMS), (TERMS, TERMS, METRIC)

    //MATRIX - (TERMS, TERMS)

    //LINE - (DATEHISTOGRAM)
}
