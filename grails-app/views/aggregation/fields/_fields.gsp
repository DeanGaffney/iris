<div id="schema-fields" class="agg-option-container col-6">
    <label class="col-2 col-form-label">Choose Field</label>
    <g:select name="agg-field-select"
              from="${schemaFields}"
              class="form-control field-type"
              type="text"
              noSelection="${['null':'Choose Field...']}"/>
</div>