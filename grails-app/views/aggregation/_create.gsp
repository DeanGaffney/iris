<div id="create-agg-schema">
    <div id="agg-wrapper" class="row">
        <div id="agg-types-container" class="row">
            <div class="col2">
                <div id="agg-buttons" class="btn-group" role="group" aria-label="Basic example">
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
        <div id="schema-es-index" class="col-10">
            <label class="col-2 col-form-label">Choose Schema</label>
            <g:select name="agg-schema-select"
                      from="${schemas}"
                      class="form-control agg-schema-type"
                      type="text"
                      optionKey="id"
                      optionValue="name"
                      noSelection="${['null':'Choose Schema...']}"/>
        </div>
    </div>
    <div id="agg-template-container"></div>
    <button type="button" id="add-agg-btn" class="btn hidden">Add</button>
    <div id="aggs-list"></div>
    <button id="build-agg-btn" type="button" class="btn">Build Aggregations</button>
    <div id="built-agg"></div>
</div>

<g:javascript>
    //schema change
    $("#agg-schema-select").on("change", function(){
        //on change make the aggregation object be a new aggregation with the new schema id
        aggregation = new Aggregation($(this).val());
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

    $("#build-agg-btn").on("click", function(){
       console.log(JSON.stringify(getRootAggregation(), null, 4));
    });
</g:javascript>


