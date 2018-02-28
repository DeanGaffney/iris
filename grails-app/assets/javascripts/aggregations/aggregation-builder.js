/**
 * Created by dean on 31/10/17.
 */
var aggregation;       // the root aggregation object
var currentAggregation;

//==================================
//      AGGREGATION BUILDING
//==================================
//the list of aggregations to be built into a single aggregation
var aggregations = [];

/**
 * Accepts an aggregation object and wraps it inside an aggregation object
 * @param aggType, the aggregation type to wrap
 * @constructor
 */
function AggregationObj(aggType){
    this.name = "aggs_" + aggregation.levels;
    this.aggs = {
        [this.name] : aggType
    }
}

/**
 * An aggregation type to create
 * @param type - the type of aggregation (avg, sum, terms etc....)
 * @param field - the field to act on
 * @constructor
 */
function AggregationType(type, field){
    this[type] = {
        field: field
    }
}

var AGG_TYPES = {
    Metric :{       //METRICS
        avg : "avg",
        cardinality: "cardinality",
        extended_stats: "extended_stats",
        max: "max",
        min: "min",
        percentile_ranks: "percentile_ranks",
        percentiles: "percentiles",
        stats: "stats",
        sum: "sum",
        value_count: "value_count"
    },
    Bucket:{
        date_histogram: "date_histogram",
        date_range: "date_range",
        filter: "filter",
        global: "global",
        histogram: "histogram",
        ip_range: "ip_range",
        missing: "missing",
        range: "range",
        significant_terms: "significant_terms",
        terms: "terms"
    }
};

/**
 * adds extra attributes to an aggregation object
 * @param type - the aggregation type
 */
function addAttributes(aggType){
    // if(isCommonMetric(aggType)){
    //     //check for missing value
    //     var missing = $("#agg-template-container").find("#metric-missing-input").val();
    //     if(missing.length > 0){
    //         aggType[Object.keys(aggType)[0]].missing = missing;
    //     }
    // }
    return aggType;
}

function isCommonMetric(aggType){
    return aggType.hasOwnProperty(AGG_TYPES.Metric.avg)             ||
           aggType.hasOwnProperty(AGG_TYPES.Metric.cardinality)     ||
           aggType.hasOwnProperty(AGG_TYPES.Metric.extended_stats)  ||
           aggType.hasOwnProperty(AGG_TYPES.Metric.max)             ||
           aggType.hasOwnProperty(AGG_TYPES.Metric.min)             ||
           aggType.hasOwnProperty(AGG_TYPES.Metric.stats)           ||
           aggType.hasOwnProperty(AGG_TYPES.Metric.sum)
}

/**
 * Adds an aggregation object to the aggregations list
 */
function addAggregation(){
    //create an aggregation object based off the type
    var templateContainer = $("#agg-template-container");
    var aggType = new AggregationType(templateContainer.find("#hidden-input").val(),
        templateContainer.find("#agg-field-select").val());   //create agg type from inputs
    aggType = addAttributes(aggType);       //add any extra attributes
    var agg = new AggregationObj(aggType);  //wrap the type in an agg object
    aggregation.aggregations.push(agg);                 //add this to the array
    currentAggregation.aggregations.push(agg);
    $("#aggs-list").html("<div class='row'><div class='agg-item'><pre>" + JSON.stringify(getCurrentAggregation(), null, 4) + "</pre></div></div>");
    $("#agg-template-container").empty();       //clear out template container
    aggregation.levels++;
    currentAggregation.levels++;
}

/**
 * Builds up a single Aggregation by nesting all sub aggregations into a single aggregation
 * @returns Root aggregation with sub aggregations inside it
 */
function buildAggregation(aggList){
    var agg;
    for (var i = aggList.length - 1; i >= 1; i--) {
        aggList[i-1] = nestAggregations(aggList[i-1], aggList[i]);
        agg = aggList[i-1];
    }
    return agg
}

function getCurrentAggregation(){
    //if list size is 1 just return that, else return result from builtAggregation()
    var currentAgg = (currentAggregation.aggregations.length == 1) ? currentAggregation.aggregations[0] : buildAggregation(currentAggregation.aggregations);
    /*add this to map to avoid sending back documents,we only want results*/
    var clone = _.clone(currentAgg);
    clone["size"] = 0;
    delete clone.name;
    //remove name from the agg, not needed for backend
    return clone;
}

/**
 * Gets the root aggregation for performing queries
 * @returns Root aggregation
 */
function getRootAggregation(){
    //if list size is 1 just return that, else return result from builtAggregation()
    var agg = (aggregation.aggregations.length == 1) ? aggregation.aggregations[0] : buildAggregation(aggregation.aggregations);
    //add this to map to avoid sending back documents,we only want results
    agg["size"] = 0;
    //remove name from the agg, not needed for backend
    delete agg.name;

    if($("#agg-most-recent-checkbox").is(":checked")){
        makeAggregationMostRecent(agg);
    }
    return agg;
}

/**
 * Embeds the sub aggregation inside the current aggregation
 * @param subAggregation - a sub aggregation to go under the current aggregation
 * @param currentAggregation - the aggregation to nest the sub aggregation
 * @returns the current aggregation with the sub aggregation inside it
 */
function nestAggregations(currentAggregation, subAggregation){
    currentAggregation.aggs[currentAggregation.name]["aggs"] = subAggregation.aggs;
    return currentAggregation;
}

/**
 * Makes an aggregation work on the most recent documents by adding a query to it
 *    "query": {
        "match_all": {}
       },
     "size": 1,
     "sort": [
     {
      "insertionDate": {
        "order": "desc"
      }
     }
     ]
 */
function makeAggregationMostRecent(agg){
    agg["query"] = {"match_all": {} };
    agg["size"] = $("#agg-size-input").val();
    agg["sort"] = [{"insertionDate": {"order" : "desc"}}];
}