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
    client.subscribe("/topic/" + schemaId + "/" + chartType, function(message) {
        console.log(JSON.stringify(message, null, 4));
        var parsedMsg = JSON.parse(message.body);
        //update the chart
        chart.load({
            columns: parsedMsg.data.columns
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
            chart.load({
                columns: data
            });
        },
        error: function(xhr, status, error) {
            console.log(xhr.responseText);
        }
    });
}
