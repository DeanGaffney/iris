<div id="create-schema-container">
    <div class="schema-form">
        <h2>Schema</h2>
        <div class="form-group">
            <label class="col-2 col-form-label">Name</label>
            <div class="col-6">
                <input readonly class="form-control" id="schema-name" required="" type="text" value="${schema.name}" >
            </div>
            <label class="col-2 col-form-label">Refresh Interval</label>
            <div class="col-6">
                <input class="form-control" id="schema-refresh" required="" type="number" value="${schema.refreshInterval}" >
            </div>
        </div>
        <div id="schema-field-container">
             <g:render template="/schemaField/edit" collection="${schema?.schemaFields}"/>
        </div>
    </div>
    <button type="button" id="add-schema-field-btn" class="btn" href="${createLink(controller: 'schemaField', action: 'form')}">Add field</button>
    <button type="button" id="update-schema-btn" class="btn" href="${createLink(controller: 'schema', action: 'update')}">Update</button>
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
        var URL = $(this).attr("href");
        appendContainerHtml(URL, REST.method.post, REST.contentType.json, {}, "#schema-field-container");
    });

    $("#update-schema-btn").on("click", function(){
        var URL = $(this).attr("href");
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