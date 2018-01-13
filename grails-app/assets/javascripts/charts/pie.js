/**
 * Created by dean on 01/11/17.
 */

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
