/**
 * Created by dean on 31/01/18.
 */


var stateDisc = new StateDisc('');

function StateDisc(schemaField){
    this.schemaField = schemaField;
    this.states = [];
    this.colours = {};
    this.labels = {};
    this.values = [];
}

function State(label, value, colour){
    this.label = label;
    this.value = value;
    this.colour = colour;
}

function getStateDiscData(stateObj){
    _.each(stateObj.states, function(el){
        stateObj.values.push(el.value);
        stateObj.colours[el.value] = el.colour;
        stateObj.labels[el.value] = el.label;
    });

    return {
        labels: stateObj.labels,
        colours: stateObj.colours,
        values: stateObj.values,
        schemaField: stateObj.schemaField
    }
}


$(document).on("change", "#schema-select", function(){
    stateDisc = new StateDisc($("#schema-field-select option:selected").text());
});

$(document).on("change", "#schema-field-select", function(){
    stateDisc = new StateDisc($("#schema-field-select option:selected").text());
});