package com.wit.iris.charts

import com.wit.iris.charts.enums.ChartType
import com.wit.iris.elastic.Aggregation
import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput
import org.grails.web.json.JSONObject

@Transactional
class ChartService {

    def socketService

    /**
     * --------------------------------------------------
     *                  UPDATING CHARTS
     * --------------------------------------------------
     */

    /**
     * Updates a dashboard chart with new aggregation result data
     * @param schemaId - the id of the schema the chart is associated with
     * @param chart - the dashboard.chart to update
     * @param data - the data to update the dashboard.chart with
     */
    void updateChart(long schemaId, Chart chart, JSONObject data){
        //check what dashboard.chart type it is
        Map formattedData = formatChartData(chart, data)
        log.debug("Formatted dashboard chart data: \n${formattedData.toString()}")
        //use socket service to send data to dashboard.chart
        socketService.sendUpdateToClient(schemaId, chart, formattedData)
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
        if(isBasicChart(chart.chartType)){
            formattedData = formatDataForBasicChart(data, chart.aggregation)
        }else if(chart.chartType == ChartType.STATE_DISC.getValue()){
            formattedData = data
        }
        return formattedData
    }

    //BAR, BUBBLE, PIE - (TERMS), (TERMS, METRIC)

    /**
     * Format data for a basic chart (Bar, Bubble, Pie, Line)
     * @param data - the data to format
     * @return A map of formatted data for a bar dashboard.chart
     */
    Map formatDataForBasicChart(JSONObject data, Aggregation agg){
        Map formattedData = [data:[:]]
        log.debug("Formatting data for Basic Chart: \n ${data.toString()}")

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

    //GROUP BAR, STACKED ROW - (TERMS, TERMS), (TERMS, TERMS, METRIC)

    //MATRIX - (TERMS, TERMS)

    //LINE - (DATEHISTOGRAM)

    /**
     * --------------------------------------------------
     *                  LOADING CHARTS
     * --------------------------------------------------
     */

    /**
     * Upon chart load data is sent to initialise the chart with data
     * @param schemaId - the schema id for the socket service
     * @param chart - the chart to update
     * @param data - the data to format for the chart
     */
    void loadChart(long schemaId, Chart chart, List<JSONObject> responses){
        Map formattedData = formatChartDataOnLoad(chart, responses)
        log.debug("Formatted dashboard chart data: \n${formattedData.toString()}")
        socketService.sendDataToClient(schemaId, chart, formattedData)
    }

    /**
     * Formats a list of aggregation results to display on chart load
     * @param chart - the chart which is being loaded
     * @param responses - list of aggregation responses from elasticsearch
     * @return Map of formatted data to suit the chart
     */
    Map formatChartDataOnLoad(Chart chart, List<JSONObject> responses){
        Map formattedData = [:]
        if(isBasicChart(chart.chartType)){
            formattedData = formatDataForBasicChartLoad(responses, chart.aggregation)
        }
        return formattedData
    }

    /**
     * Formats a list of aggregation results for the chart type
     * @param responses - the list of aggregation responses from elasticsearch
     * @param agg - the aggregation used to get the results
     * @return Map of formatted data to suit the chart
     */
    Map formatDataForBasicChartLoad(List<JSONObject> responses, Aggregation agg){
        Map formattedData = [data:[:]]

        String option = (agg.levels == 1) ? "doc_count" : "value"
        List keys = responses[0].aggregations."aggs_1".buckets*.key
        log.debug("Chart keys: ${keys.toString()}")

        List valueLists = []        //a list to hold all value lists extracted from the aggregation responses

        //loop over all responses and add their values list into the values list, to form a list of lists
        responses.each { ele ->
            if(agg.levels == 1){
                valueLists.add(ele.aggregations."aggs_1".buckets*."$option")
            }else{
                valueLists.add(ele.aggregations."aggs_1".buckets*."aggs_2"."$option")
            }
        }

        log.debug("Chart values: ${valueLists.toString()}")
        List columns = []   //list containing the formatted data for chart
        keys.eachWithIndex{ele, indx -> columns.add([ele])}      //create lists starting with the keys as the first element in each list

        valueLists.each{ valueList ->       //for each list in the values list
            keys.size().times {indx  ->     // obtain the value from the response and add it to the columns list of lists
                List col = columns[indx]        //TODO groovy is having issues with calling columns[indx].add(), not picking up that columns[indx] returns a list, clean this up at some point
                col.add(valueList[indx])
                columns[indx] = col
            }
        }

        formattedData.data.columns = columns
        log.debug("Formatted data for Chart: ${formattedData.toString()}")
        return formattedData
    }

    /**
     * --------------------------------------------------
     *                COMMON CHART UTILS
     * --------------------------------------------------
     */

    /**
     * Checks to see if the chart type is consider a basic chart
     * @param chartType - the type of chart (Bar, Bubble, Pie etc....)
     * @return true if the chart is considered basic (Bar, Bubble, Pie, Line)
     */
    boolean isBasicChart(String chartType){
        return chartType in [ChartType.BAR.getValue(),
                             ChartType.BUBBLE.getValue(),
                             ChartType.PIE.getValue(),
                             ChartType.LINE.getValue()]
    }
}
