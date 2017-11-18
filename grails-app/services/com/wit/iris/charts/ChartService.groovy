package com.wit.iris.charts

import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject

@Transactional
class ChartService {

    def socketService

    /**
     * Updates a chart with new aggregation result data
     * @param chart - the chart to update
     * @param data - the data to update the chart with
     * @param agg - the aggregation that was used to get the data
     */
    void updateChart(String esIndex, Chart chart, JSONObject data, Aggregation agg){
        //check what chart type it is
        Map formattedData = formatChartData(chart, data, agg)
        log.debug("Formatted chart data ${formattedData.toString()}")
        //use socket service to send data to chart
        socketService.sendDataToClient(esIndex, chart.chartType, formattedData)
    }

    /**
     * Format aggregation result data to suit specified chart
     * @param chart - the chart to format the data for
     * @param data - the aggregation result data
     * @return formatted data for chart
     */
    Map formatChartData(Chart chart, JSONObject data, Aggregation agg){
        Map formattedData = [:]
        if(chart.chartType == ChartType.BAR.getValue()){
            formattedData = formatDataForBar(data, agg)
        }
        return formattedData
    }

    /**
     * Format data for a bar chart
     * @param data - the data to format
     * @return A map of formatted data for a bar chart
     */
    Map formatDataForBar(JSONObject data, Aggregation agg){
        if(agg.levels == 1){
            // a terms was applied
            data.aggregations.agg1.buckets*.key
            // data.aggregations.agg1.buckets*.key
            // data.aggregations.agg1.buckets*.doc_count
        }else{
            // a terms with a metric was applied
            // data.aggregations.agg1.buckets*.key
            // data.aggregations.agg1.buckets*.agg2.value
        }
    }

    //BAR, BUBBLE, PIE - (TERMS), (TERMS, METRIC)

    //GROUP BAR, STACKED ROW - (TERMS, TERMS), (TERMS, TERMS, METRIC)

    //MATRIX - (TERMS, TERMS)

    //LINE - (DATEHISTOGRAM)
}
