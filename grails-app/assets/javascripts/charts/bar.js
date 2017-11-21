/**
 * Created by dean on 01/11/17.
 */

function BarChart(containerSelector, data){
    this.chart = bb.generate({
        bindto: containerSelector,
        data: {
            type: "bar",
            columns: data
        }
    });

    this.update = function(data){
        data = [
            ["data1",  Math.floor(Math.random() * 20)],
            ["data2",  Math.floor(Math.random() * 20)]
        ];
        this.chart.flow({
            columns: data,
            length: 0
        });
    }
}
