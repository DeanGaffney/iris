/**
 * Created by dean on 19/01/18.
 */

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
        },
        axis:{
            x:{
                show: true
            },
            y:{
                show: true
            }
        },
        bar:{
            width: 5,
            zerobased: true
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
            },
            y: {
                max: 450
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
            onclick: function (d, i) { console.log("onclick", d, i); },
            onover: function (d, i) { console.log("onover", d, i); },
            onout: function (d, i) { console.log("onout", d, i); },
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