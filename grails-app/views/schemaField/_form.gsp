<div id="row" class="schema-field-form">

    <h3>Schema Field</h3>
    <div class="form-group">
        <label class="col-2 col-form-label">Field Name</label>
        <div class="col-6">
            <input class="form-control" id="schema-field-name" required="" type="text" value="" >
        </div>
        <label class="col-2 col-form-label">Field Type</label>
        <div class="col-6">
            <g:select name="schema-field-select" from="${fieldTypes}" class="form-control" id="schema-field-type" type="text" value="" />
        </div>
    </div>

    <button id="schema-field-confirm-btn" class="btn">Add<span class="fa fa-check"></span></button>
    <button id="clear-field-btn" class="btn">Clear<span class="fa fa-trash"></span></button>
</div>


<g:javascript>

    var tableEntries = 0;

    $("#schema-field-confirm-btn").on("click", function(){
       //add the field to the table
        var field = new SchemaField($("#schema-field-name").val(), $("#schema-field-type").val());

        $("#schema-fields-table > tbody").append('<tr><th scope="row"></th><td class="schema-field-name">' +  field.name + '</td><td class="schema-field-type">' +  field.fieldType + '</td></tr>');

        tableEntries = $("#schema-fields-table > tbody > tr").length;

        $("#schema-fields-table > tbody > tr:last-child > th").html(tableEntries);

        console.log()

        $("#schema-fields-title").text(($("#schema-fields-title").text().replace(/\d+/g, tableEntries)));

        // clear the field area
        clearFieldArea();
    });

    $("#clear-field-btn").on("click", function () {
        //clear the field area
        clearFieldArea();
    });

    function clearFieldArea(){
        $("#schema-field-container").html("");
    }
</g:javascript>