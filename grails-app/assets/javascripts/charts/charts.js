/**
 * Created by dean on 19/01/18.
 */

var chartTypes = {
    Bar: "Bar",
    Bubble: "Bubble",
    Pie: "Pie",
    Line: "Line",
    StateDisc: "StateDisc",
    StateList: "StateList"
};

var chartPatterns = ["#12EED8", "#FFFF00", "#AF4BFF", "#BC2C4B"];

function BarChart(containerSelector, data){
    this.chart = bb.generate({
        bindto: containerSelector,
        data: {
            type: "bar",
            columns: data
        },
        color:{
            pattern: chartPatterns
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
        color:{
            pattern: chartPatterns
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
            type: "pie"
        },
        color:{
            pattern: chartPatterns
        },
        bindto: containerSelector
    });
}

function LineChart(cssSelector, data){
    this.chart = bb.generate({
        data: {
            columns: data
        },
        color:{
            pattern: chartPatterns
        },
        bindto: cssSelector
    });
}

function StateDiscChart(cssSelector, data){
    this.chart = bb.generate({
        data: {
            columns: data,
            type: "pie"
        },
        pie:{
            label:{
                show : true,
                format: function(value, ratio, id) {
                    return value;
                }
            }
        },
        bindto: cssSelector
    });

    this.labels = data.labels;

    this.colours = data.colours;

}