/**
 * Created by dean on 01/11/17.
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
        bar:{
            width:{
                ratio: 0.5
            }
        }
    });
}
