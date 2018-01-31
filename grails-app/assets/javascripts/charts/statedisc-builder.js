/**
 * Created by dean on 31/01/18.
 */


var stateDisc;

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