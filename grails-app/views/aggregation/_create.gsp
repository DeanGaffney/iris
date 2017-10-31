<div id="create-agg-schema">
    <div id="agg-wrapper" class="row">
        <div id="agg-types-container" class="row">
            <div class="col2">
                <div id="agg-buttons" class="btn-group" role="group" aria-label="Basic example">
                    <button id="avg-btn" type="button" class="btn" href="${createLink(controller: 'aggregation', action: 'getAvgTemplate')}">Average</button>
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
    <button id="add-agg-btn" class="btn">Add</button>
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
        updateContainerHtml(URL, REST.method.post, REST.contentType.json,
           {schemaId: aggregation.schemaId}, "#agg-template-container");
    });

    //click add button
    $("#add-agg-btn").on("click", function(){
        //create an aggregation object based off the type
        //add attributes to it in the addAttribute factory method
        //retrieve the new object after adding the attributes
        //wrap the obj in an aggregation object
        //add to the aggregations array
    });
</g:javascript>


