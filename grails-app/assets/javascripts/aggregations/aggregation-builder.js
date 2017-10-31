/**
 * Created by dean on 31/10/17.
 */
var aggregation;

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
    this.aggs = aggType;
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
 * @param type
 */
function addAttributes(type){
    if(type == AGG_TYPES.Metric.avg){
        //check for missing value
        var missing = $("#agg-template-container").find("#metric-missing-input").val();
        console.log(missing);
    }
}