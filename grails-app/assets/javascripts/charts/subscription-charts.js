/**
 * Created by dean on 17/01/18.
 */

function getSubscriptionChart(subscriptionId, chartType, containerSelector, schemaId, chartData, title){
    var chart;
    if(chartType == chartTypes.Bar){
        chart = new BarChart(containerSelector, [], title);
    }else if(chartType == chartTypes.Pie){
        chart = new PieChart(containerSelector, [], title);
    }else if(chartType == chartTypes.Bubble){
        chart = new BubbleChart(containerSelector, [], title);
    }else if(chartType == chartTypes.Line){
        chart = new LineChart(containerSelector, [], title);
    }else if(chartType == chartTypes.StateDisc){
        chart = new StateDiscChart(containerSelector, chartData, title);
    }
    setChartSubscription(subscriptionId, chart, chartType, schemaId);
    return chart.instance;
}

function setChartSubscription(subscriptionId, chart, chartType, schemaId){
    //this subscription is for updates being sent to the chart
    client.subscribe("/topic/" + schemaId + "/" + chartType + "/" + subscriptionId, function(message) {
        var parsedMsg = JSON.parse(message.body);
        //update the chart
        if(chartType != chartTypes.StateDisc){  //REGULAR CHARTS
            updateBasicCharts(chart, parsedMsg);
        }else{  //STATE BASED CHARTS
            updateStateDiscChart(chart, parsedMsg);
        }
    });

    //this subscription is for initial loading data for the chart
    client.subscribe("/topic/load/" + schemaId + "/" + chartType + "/" + subscriptionId, function(message){
        var parsedMsg = JSON.parse(message.body);
       // update the chart
        chart.instance.load({
            columns: parsedMsg.data.columns,
            length: 0
        });

    });
}

function updateBasicCharts(chart, parsedJson){
    var dataLengths = chart.instance.data().map(col => col.values.map(item => item.value))
                                             .map(values => values.length);
    var maxListLength = 0;
    for(var i = 0; i < dataLengths.length; i++){
        if(maxListLength < dataLengths[i]){
            maxListLength = dataLengths[i]
        }
    }

    var length = (maxListLength >= 5) ? 1 : 0;
    chart.instance.flow({
        columns: parsedJson.data.columns,
        length: length
    });
}

function updateStateDiscChart(chart, parsedJson){
    var val = parsedJson[chart.schemaField];

    //if there is no values found, the incoming state is new so unload and update
    if(chart.instance.data.values(chart.labels[val]) == null){
        var color = chart.colours[val];
        var label = chart.labels[val];
        var dataToUnload = chart.instance.data()[0].id;

        chart.instance.load({
            columns: [
                [label, val]
            ],
            unload:[dataToUnload],
            colors:{
                [label]: color
            }
        });

        chart.instance.flush();
    }
}

function onChartLoad(controllerUrl, data){
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
