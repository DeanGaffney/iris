/**
 * Created by dean on 09/01/18.
 */

var placeholderTitle = "Placeholder";

function getPlaceHolderChart(chartType, containerSelector, chartData){
    var chart;
    if(chartType == chartTypes.Bar){
        chart = new BarChart(containerSelector, placeholderData[chartType], placeholderTitle + " Bar Chart").instance;
    }else if(chartType == chartTypes.Pie){
        chart = new PieChart(containerSelector, placeholderUpdateData[chartType], placeholderTitle + " Pie Chart").instance;
    }else if(chartType == chartTypes.Bubble){
        chart = new BubbleChart(containerSelector, placeholderData[chartType], placeholderTitle + " Bubble Chart").instance;
    }else if(chartType == chartTypes.Line){
        chart = new LineChart(containerSelector, placeholderData.Line, placeholderTitle + " Line Chart").instance;
    }else if(chartType == chartTypes.StateDisc){
        chart = new StateDiscChart(containerSelector, chartData, placeholderTitle + " State Disc Chart").instance;
    }

    if(chartType != chartType.StateDisc){
        updatePlaceholderChart(chart, placeholderUpdateData[chartType]);
    }
    return chart;
}

var placeholderData = {
    Bar: [["data1", 30, 200, 100, 170, 150, 250],
          ["data2", 130, 100, 140, 35, 110, 50]],

    Pie: [["data1", 0.2, 0.2, 0.2, 0.2, 0.2, 0.4, 0.3, 0.2, 0.2, 0.1, 0.2, 0.2, 0.1, 0.1, 0.2, 0.4, 0.4, 0.3, 0.3, 0.3, 0.2, 0.4, 0.2, 0.5, 0.2, 0.2, 0.4, 0.2, 0.2, 0.2, 0.2, 0.4, 0.1, 0.2, 0.2, 0.2, 0.2, 0.1, 0.2, 0.2, 0.3, 0.3, 0.2, 0.6, 0.4, 0.3, 0.2, 0.2, 0.2, 0.2],
          ["data2", 1.4, 1.5, 1.5, 1.3, 1.5, 1.3, 1.6, 1.0, 1.3, 1.4, 1.0, 1.5, 1.0, 1.4, 1.3, 1.4, 1.5, 1.0, 1.5, 1.1, 1.8, 1.3, 1.5, 1.2, 1.3, 1.4, 1.4, 1.7, 1.5, 1.0, 1.1, 1.0, 1.2, 1.6, 1.5, 1.6, 1.5, 1.3, 1.3, 1.3, 1.2, 1.4, 1.2, 1.0, 1.3, 1.2, 1.3, 1.3, 1.1, 1.3],
          ["data3", 2.5, 1.9, 2.1, 1.8, 2.2, 2.1, 1.7, 1.8, 1.8, 2.5, 2.0, 1.9, 2.1, 2.0, 2.4, 2.3, 1.8, 2.2, 2.3, 1.5, 2.3, 2.0, 2.0, 1.8, 2.1, 1.8, 1.8, 1.8, 2.1, 1.6, 1.9, 2.0, 2.2, 1.5, 1.4, 2.3, 2.4, 1.8, 1.8, 2.1, 2.4, 2.3, 1.9, 2.3, 2.5, 2.3, 1.9, 2.0, 2.3, 1.8]],

    Line: [["data1", 30, 200, 100, 170, 150, 250],
            ["data2", 130, 100, 140, 35, 110, 50]],

    Bubble: [["data1", 30, 350, 200, 380, 150, 250, 50, 80, 55, 220],
            ["data2", 130, 100, 10, 200, 80, 50, 200, 123, 185, 98],
            ["data3", 230, 153, 85, 300, 250, 120, 5, 84, 99, 289]]
};

var placeholderUpdateData = {
    Bar: [["data1",  Math.floor(Math.random() * 250)],
         ["data2",   Math.floor(Math.random() * 250)]],

    Pie: [["data1",  Math.floor(Math.random() * 3.0)],
          ["data2",  Math.floor(Math.random() * 3.0)],
          ["data3",  Math.floor(Math.random() * 3.0)]],

    Line: [["data1",  Math.floor(Math.random() * 250)],
           ["data2",   Math.floor(Math.random() * 250)]],

    Bubble: [["data1",  Math.floor(Math.random() * 300)],
            ["data2",  Math.floor(Math.random() * 300)],
            ["data3",  Math.floor(Math.random() * 300)]]

};

function updatePlaceholderChart(chartToUpdate, data){
    setInterval(function(){
        chartToUpdate.flow({
            columns: data,
            length: 1
        });
    },3000);
}