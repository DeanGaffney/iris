/**
 * Created by dean on 17/01/18.
 */
/**
 * Created by dean on 01/11/17.
 */

function getSubscriptionChart(chartType, containerSelector, schemaId){
    var chart;
    if(chartType == "Bar"){
        chart = new BarChart(chartType, containerSelector).chart;
        setChartSubscription(chart, chartType, schemaId);
    }

    return chart;
}

function setChartSubscription(chart, chartType, schemaId){
    client.subscribe("/topic/" + schemaId + "/" + chartType, function(message) {
        console.log("Received a message");
        console.log(JSON.stringify(message, null, 4));
        //update the chart
    });
}
