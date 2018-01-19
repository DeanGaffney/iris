/**
 * Created by dean on 17/01/18.
 */
/**
 * Created by dean on 01/11/17.
 */

function getSubscriptionChart(chartType, containerSelector, schemaId){
    var chart;
    if(chartType == "Bar"){
        chart = new BarChart(containerSelector, []).chart;
    }else if(chartType == "Pie"){
        chart = new PieChart(containerSelector, []).chart;
    }else if(chartType == "Bubble"){
        chart = new BubbleChart(containerSelector, []).chart;
    }else if(chartType == "Line"){
        chart = new LineChart(containerSelector, []).chart;
    }
    setChartSubscription(chart, chartType, schemaId);
    return chart;
}

function setChartSubscription(chart, chartType, schemaId){
    client.subscribe("/topic/" + schemaId + "/" + chartType, function(message) {
        console.log("Received a message");
        console.log(JSON.stringify(message, null, 4));
        var parsedMsg = JSON.parse(message.body);
        //update the chart
        chart.flow({
            columns: parsedMsg.data.columns
        });
    });
}
