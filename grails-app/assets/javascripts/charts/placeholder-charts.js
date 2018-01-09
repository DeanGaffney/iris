/**
 * Created by dean on 09/01/18.
 */


function getPlaceHolderChart(chartType, containerSelector){
    var chart;
    if(chartType == "Bar"){
        chart = new BarChart(containerSelector, placeholderData.BAR);
    }
    return chart;
}

var placeholderData = {
    BAR: [["data1", 30, 200, 100, 170, 150, 250],
          ["data2", 130, 100, 140, 35, 110, 50]]
};