/**
 * Created by dean on 31/01/18.
 */


var stateDisc = new StateDisc('', '');

function StateDisc(schemaId, schemaField){
    this.schemaId = schemaId;
    this.schemaField = schemaField;
    this.states = [];
}

function State(label, value, colour){
    this.label = label;
    this.value = value;
    this.colour = colour;
}


$(document).on("change", "#schema-select", function(){
    stateDisc = new StateDisc($("#schema-select").val(), $("#schema-field-select option:selected").text());
});

$(document).on("change", "#schema-field-select", function(){
    stateDisc = new StateDisc($("#schema-select").val(), $("#schema-field-select option:selected").text());
});