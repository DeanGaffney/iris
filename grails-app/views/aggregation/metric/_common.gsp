<div class="metric-agg">
    <div class="metric-fields">
        <div id="schema-fields" class="col-6">
            <label class="col-2 col-form-label">Choose Field</label>
            <g:select name="agg-field-select"
                      from="${schemaFields}"
                      class="form-control field-type"
                      type="text"
                      noSelection="${['null':'Choose Field...']}"/>
        </div>
        <g:if test="${hiddenValue != 'value_count'}">
        <div id="metric-missing" class="col-6">
            <label class="col-2 col-form-label">Missing</label>
            <div class="col-6">
                <input id="metric-missing-input" class="form-control" type="number" value="" >
            </div>
        </g:if>
            <div class="col-6">
                <input id="hidden-input" class="form-control" type="hidden" value="${hiddenValue}" >
            </div>
        </div>
    </div>
</div>
