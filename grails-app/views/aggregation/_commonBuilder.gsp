
<div class="row">
    <div id="agg-buttons-wrapper" class="col-8">
        <h5>Aggregation Options</h5>
        <div id="agg-buttons">
            %{--DO NOT CHANGE THE IDS ON THESE BUTTONS!!!!!--}%
            <button id="avg-btn" type="button" class="btn" href="${createLink(controller: 'aggregation', action: 'getMetricTemplate')}">Average</button>
            <button id="cardinality-btn" type="button" class="btn" href="${createLink(controller: 'aggregation', action: 'getMetricTemplate')}">Cardinality</button>
            <button id="extended_stats-btn" type="button" class="btn" href="${createLink(controller: 'aggregation', action: 'getMetricTemplate')}">Extended Stats</button>
            <button id="max-btn" type="button" class="btn" href="${createLink(controller: 'aggregation', action: 'getMetricTemplate')}">Max</button>
            <button id="min-btn" type="button" class="btn" href="${createLink(controller: 'aggregation', action: 'getMetricTemplate')}">Min</button>
            <button id="stats-btn" type="button" class="btn" href="${createLink(controller: 'aggregation', action: 'getMetricTemplate')}">Stats</button>
            <button id="sum-btn" type="button" class="btn" href="${createLink(controller: 'aggregation', action: 'getMetricTemplate')}">Sum</button>
            <button id="value_count-btn" type="button" class="btn" href="${createLink(controller: 'aggregation', action: 'getMetricTemplate')}">Value Count</button>
            <button id="terms-btn" type="button" class="btn" href="${createLink(controller: 'aggregation', action: 'getBucketTemplate')}">Terms</button>
        </div>
    </div>
</div>

<div class="row">
    <div id="agg-template-container" class="col-8"></div>
</div>

<div class="row">
    <div id="aggs-list-wrapper" class="col-8">
        <h5>Current Aggregation:</h5>
        <div id="aggs-list"></div>
    </div>
</div>


<div class="row">
    <button type="button" id="add-agg-btn" class="btn hidden">Add</button>
    <button id="test-agg-btn" type="button" class="btn" href="${createLink(controller: 'aggregation', action: 'getAggregationResult')}">Test Aggregation</button>
    <button type="button" id="clear-agg-btn" class="btn">Clear<span class="fa fa-trash"></span></button>
</div>

<div class="row">
    <div id="agg-result-container" class="col-8"></div>
</div>


<g:javascript>
    //schema change
    $("#schema-select").on("change", function(){
        //on change make the aggregation object be a new aggregation with the new schema id
        aggregation = new Aggregation($(this).val());
        currentAggregation = new Aggregation($(this).val());
    });

    //agg type button clicked
    //on click average button
    $("#agg-buttons > button").on("click", function(){
        const URL = $(this).attr("href");
        var aggType = $(this).attr("id").replace("-btn", "");       //remove btn on id, used in backend
        updateContainerHtml(URL, REST.method.post, REST.contentType.json,
                {schemaId: aggregation.schemaId, aggType : aggType}, "#agg-template-container");
    });

    //click add button
    $("#add-agg-btn").on("click", function(){
        addAggregation();
    });

    $("#test-agg-btn").on("click", function(){
        const URL = $(this).attr("href");
        aggregation.json = (_.isEmpty(aggregation.json)) ? getRootAggregation() : aggregation.json;
        var agg = {
            schemaId: aggregation.schemaId,
            aggJson: aggregation.json,
            aggLevels: aggregation.levels
        }
        prettyPrintJsonResponse(URL, REST.method.post, REST.contentType.json, agg, "#agg-result-container");
    });

    $("#clear-agg-btn").on("click", function(){
        //reset aggregation object
        aggregation.init();
        currentAggregation.init();
        $("#agg-result-container").html("");
        $("#aggs-list").html("");
    });
</g:javascript>