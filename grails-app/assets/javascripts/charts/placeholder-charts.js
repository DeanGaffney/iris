/**
 * Created by dean on 09/01/18.
 */


function getPlaceHolderChart(chartType, containerSelector){
    var chart;
    if(chartType == "Bar"){
        chart = new BarChart(containerSelector, placeholderData.BAR).chart;
        updatePlaceholderChart(chart, placeholderUpdateData.BAR);
    }else if(chartType == "Pie"){
        chart = new PieChart(containerSelector, placeholderUpdateData.PIE).chart;
        updatePlaceholderChart(chart, placeholderUpdateData.PIE);
    }
    return chart;
}

var placeholderData = {
    BAR: [["data1", 30, 200, 100, 170, 150, 250],
          ["data2", 130, 100, 140, 35, 110, 50]],

    PIE: [["data1", 0.2, 0.2, 0.2, 0.2, 0.2, 0.4, 0.3, 0.2, 0.2, 0.1, 0.2, 0.2, 0.1, 0.1, 0.2, 0.4, 0.4, 0.3, 0.3, 0.3, 0.2, 0.4, 0.2, 0.5, 0.2, 0.2, 0.4, 0.2, 0.2, 0.2, 0.2, 0.4, 0.1, 0.2, 0.2, 0.2, 0.2, 0.1, 0.2, 0.2, 0.3, 0.3, 0.2, 0.6, 0.4, 0.3, 0.2, 0.2, 0.2, 0.2],
          ["data2", 1.4, 1.5, 1.5, 1.3, 1.5, 1.3, 1.6, 1.0, 1.3, 1.4, 1.0, 1.5, 1.0, 1.4, 1.3, 1.4, 1.5, 1.0, 1.5, 1.1, 1.8, 1.3, 1.5, 1.2, 1.3, 1.4, 1.4, 1.7, 1.5, 1.0, 1.1, 1.0, 1.2, 1.6, 1.5, 1.6, 1.5, 1.3, 1.3, 1.3, 1.2, 1.4, 1.2, 1.0, 1.3, 1.2, 1.3, 1.3, 1.1, 1.3],
          ["data3", 2.5, 1.9, 2.1, 1.8, 2.2, 2.1, 1.7, 1.8, 1.8, 2.5, 2.0, 1.9, 2.1, 2.0, 2.4, 2.3, 1.8, 2.2, 2.3, 1.5, 2.3, 2.0, 2.0, 1.8, 2.1, 1.8, 1.8, 1.8, 2.1, 1.6, 1.9, 2.0, 2.2, 1.5, 1.4, 2.3, 2.4, 1.8, 1.8, 2.1, 2.4, 2.3, 1.9, 2.3, 2.5, 2.3, 1.9, 2.0, 2.3, 1.8],]
};

var placeholderUpdateData = {
    BAR: [["data1",  Math.floor(Math.random() * 20)],
         ["data2",   Math.floor(Math.random() * 20)]],

    PIE: [["data1",  Math.floor(Math.random() * 20)],
          ["data2",  Math.floor(Math.random() * 20)],
          ["data3",  Math.floor(Math.random() * 20)]]
};

function updatePlaceholderChart(chartToUpdate, data){
    setInterval(function(){
        chartToUpdate.flow({
            columns: data,
            length: 0
        });
    },2000);
}