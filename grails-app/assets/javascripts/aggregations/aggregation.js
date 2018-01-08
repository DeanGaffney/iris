/**
 * Created by dean on 31/10/17.
 */
function Aggregation(schemaId){
    this.schemaId = schemaId;
    this.aggregations;
    this.levels;
    this.json;
    this.init = function(){
        this.aggregations = [];
        this.levels = 1;
        this.json = {};
    };
    this.init();
}