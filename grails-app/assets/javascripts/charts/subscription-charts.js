/**
 * Created by dean on 17/01/18.
 */

function getSubscriptionChart(chartType, containerSelector, schemaId){
    var chart;
    if(chartType == chartTypes.Bar){
        chart = new BarChart(containerSelector, []).chart;
    }else if(chartType == chartTypes.Pie){
        chart = new PieChart(containerSelector, []).chart;
    }else if(chartType == chartTypes.Bubble){
        chart = new BubbleChart(containerSelector, []).chart;
    }else if(chartType == chartTypes.Line){
        chart = new LineChart(containerSelector, []).chart;
    }
    setChartSubscription(chart, chartType, schemaId);
    return chart;
}

function setChartSubscription(chart, chartType, schemaId){
    //this subscription is for updates being sent to the chart
    client.subscribe("/topic/" + schemaId + "/" + chartType, function(message) {
        var parsedMsg = JSON.parse(message.body);
        //update the chart
        chart.flow({
            columns: parsedMsg.data.columns,
            length: 1
        });
    });

    //this subscription is for initial loading data for the chart
    client.subscribe("/topic/load/" + schemaId + "/" + chartType, function(message){
        var parsedMsg = JSON.parse(message.body);
        //update the chart
        chart.load({
            columns: parsedMsg.data.columns,
            length: 0
        });
    });
}

function onChartLoad(controllerUrl, data, chart){
    $.ajax({
        url: controllerUrl,
        type: REST.method.post,
        dataType: REST.dataType.json,
        contentType: REST.contentType.json,
        data: JSON.stringify(data),
        success: function(data){

        },
        error: function(xhr, status, error) {
            console.log(xhr.responseText);
        }
    });
}
