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

function BarChart(containerSelector, data, title){
    this.instance = bb.generate({
        bindto: containerSelector,
        data: {
            type: "bar",
            columns: data
        },
        color:{
            pattern: chartPatterns
        },
        title:{
            text: title,
            padding: {
                top: 10,
                right: 10,
                bottom: 10,
                left: 10
            },
            position: "top-center"
        },
    });
}


function BubbleChart(cssSelector, data, title) {
    this.instance = bb.generate({
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
        title:{
            text: title,
            padding: {
                top: 10,
                right: 10,
                bottom: 10,
                left: 10
            },
            position: "top-center"
        },
        bindto: cssSelector
    });
}

function PieChart(containerSelector, data, title){
    this.instance = bb.generate({
        data: {
            columns: data,
            type: "pie"
        },
        color:{
            pattern: chartPatterns
        },
        title:{
            text: title,
            padding: {
                top: 10,
                right: 10,
                bottom: 10,
                left: 10
            },
            position: "top-center"
        },
        bindto: containerSelector
    });
}

function LineChart(cssSelector, data, title){
    this.instance = bb.generate({
        data: {
            columns: data
        },
        color:{
            pattern: chartPatterns
        },
        title:{
            text: title,
            padding: {
                top: 10,
                right: 10,
                bottom: 10,
                left: 10
            },
            position: "top-center"
        },
        bindto: cssSelector
    });
}

function StateDiscChart(cssSelector, data, title){
    this.instance = bb.generate({
        data: {
            columns: [  //default to the first state for placeholders
                [data.labels[data.values[0]], parseInt(data.values[0])]
            ],
            type: "pie",
            colors:{
                [data.labels[data.values[0]]]: data.colours[data.values[0]]
            }
        },
        pie:{
            label:{
                show : true,
                format: function(value, ratio, id) {
                    return value;
                }
            }
        },
        title:{
            text: title,
            padding: {
                top: 10,
                right: 10,
                bottom: 10,
                left: 10
            },
            position: "top-center"
        },
        bindto: cssSelector
    });

    this.labels = data.labels;

    this.colours = data.colours;

    this.schemaField = data.schemaField;
}