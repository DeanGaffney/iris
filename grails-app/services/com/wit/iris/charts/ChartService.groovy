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
    void updateChart(String esIndex, Chart chart, JSONObject data){
        //check what chart type it is
        Map formattedData = formatChartData(chart, data)
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
    Map formatChartData(Chart chart, JSONObject data){
        Map formattedData = [:]
        if(chart.chartType == ChartType.BAR.getValue()){
            formattedData = formatDataForBar(data, chart.aggregation)
        }
        return formattedData
    }

    /**
     * Format data for a bar chart
     * @param data - the data to format
     * @return A map of formatted data for a bar chart
     */
    Map formatDataForBar(JSONObject data, Aggregation agg){
        Map formattedData = [data:[:]]
        List keys, values
        String option = (agg.levels == 1) ? "doc_count" : "value"
        keys = data.aggregations.agg1.buckets*.key
        if(agg.levels == 1){
            values = data.aggregations.agg1.buckets*."$option"
        }else{
            values = data.aggregations.agg1.buckets*.agg2*."$option"
        }
        log.debug("Chart keys: ${keys.toString()}")
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
