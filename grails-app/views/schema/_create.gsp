<div id="create-schema-container">
    <div class="schema-form">
        <h2>Schema</h2>
        <div class="form-group">
            <label class="col-2 col-form-label">Name</label>
            <div class="col-6">
                <input class="form-control" id="schema-name" required="" type="text" value="" >
            </div>
            <label class="col-2 col-form-label">Refresh Interval</label>
            <div class="col-6">
                <input class="form-control" id="schema-refresh" required="" type="number" value="" >
            </div>
        </div>
        <div id="schema-field-container"></div>
    </div>
    <button type="button" id="add-schema-field-btn" class="btn btn-primary" href="${createLink(controller: 'schemaField', action: 'form')}">Add field</button>
    <button type="button" id="save-schema-btn" class="btn btn-primary" href="${createLink(controller: 'schema', action: 'save')}">Save</button>
</div>
<g:javascript>

    var schema = function(name, refreshInterval){
        this.name = name;
        this.refreshInterval = refreshInterval;
        this.schemaFields = [];
    }

    var schemaField = function(name, fieldType){
        this.name = name;
        this.fieldType = fieldType;
    }

    $("#add-schema-field-btn").on( "click", function(){
        const URL = $(this).attr("href");
        appendContainerHtml(URL, REST.method.post, REST.contentType.json, {}, "#schema-field-container");
    });

    $("#save-schema-btn").on("click", function(){
        const URL = $(this).attr("href");
        //create schema object from name and refresh interval
        var schemaObj = new schema($("#schema-name").val(), $("#schema-refresh").val());
        $(".schema-field-form").each(function(){
            var schemaFieldObj = new schemaField($(this).find(".schema-field-name").val(), $(this).find(".schema-field-type").val());
            //add this obj to schema array
            schemaObj.schemaFields.push(schemaFieldObj);
        });
        updateContainerHtml(URL, REST.method.post, REST.contentType.json, schemaObj,"#schema-main-container");
    });
</g:javascript>