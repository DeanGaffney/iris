/**
 * Created by dean on 19/01/18.
 */

var chartTypes = {
    Bar: "Bar",
    Bubble: "Bubble",
    Pie: "Pie",
    Line: "Line"
};

function BarChart(containerSelector, data){
    this.chart = bb.generate({
        bindto: containerSelector,
        data: {
            type: "bar",
            columns: data,
            colors:{
                data1: "#12EED8",
                data2: "#FFFF00"
            }
        }
    });
}


function BubbleChart(cssSelector, data) {
    this.chart = bb.generate({
        data: {
            columns: data,
            type: "bubble",
            labels: true
        },
        bubble: {
            maxR: 50
        },
        axis: {
            x: {
                type: "category"
            }
        },
        bindto: cssSelector
    });
}

function PieChart(containerSelector, data){
    this.chart = bb.generate({
        data: {
            columns: data,
            type: "pie",
            colors:{
                data1: "#12EED8",
                data2: "#FFFF00",
                data3: "#AF4BFF"
            }
        },
        bindto: containerSelector
    });
}

function LineChart(cssSelector, data){
    this.chart = bb.generate({
        data: {
            columns: data
        },
        bindto: cssSelector
    });
}