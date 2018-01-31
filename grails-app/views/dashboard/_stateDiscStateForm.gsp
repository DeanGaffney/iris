<div class="row">
    <div class="form-group" class="col-4">
        <label for="state-label">State Label</label>
        <input type="text" class="form-control" id="state-label" placeholder="Enter state label">
    </div>

    <div class="form-group" class="col-4">
        <label for="state-value">State Value</label>
        <input type="number" class="form-control" id="state-value">
    </div>

    <div class="form-group" class="col-4">
        <label for="state-color">State Colour</label>
        <div id="state-color" class="input-group colorpicker-component" title="State Colour Picker">
            <input type="text" class="form-control input-lg" value="#DD0F20"/>
            <span class="input-group-addon"><i></i></span>
        </div>
    </div>
    <button class="btn" id="confirm-state-btn">Add State</button>
</div>

<g:javascript>

    var cp = $("#state-color").colorpicker({
        useAlpha: false
    });

    $("#confirm-state-btn").on("click", function(){
        stateDisc.states.push(new State($("#state-label").val(), $("#state-value").val(), cp.colorpicker('getValue')));
        $("#state-disc-form-area").html("");
    });
</g:javascript>