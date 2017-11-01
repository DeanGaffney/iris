/**
 * Created by dean on 01/11/17.
 */

function bar(containerSelector){
    var chart = bb.generate({
        bindto: containerSelector,
        data: {
            type: "bar",
            columns: [
                ["data1", 30, 200, 100, 170, 150, 250],
                ["data2", 130, 100, 140, 35, 110, 50]
            ]
        }
    });
}
