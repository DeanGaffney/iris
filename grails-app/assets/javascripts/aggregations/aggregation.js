/**
 * Created by dean on 31/10/17.
 */

/**
 * This Aggregation object is used in the Aggregation Playground
 * @param schemaId - id of schema from playground
 * @constructor
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
