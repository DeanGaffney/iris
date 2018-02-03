<div id="schema-area">
    <label for="schema-select-area">Select Schema</label>
    <div id="schema-select-area" class="form-group">
        <g:select name="schema-select"
                  from="${com.wit.iris.schemas.IrisSchema.list()}"
                  class="col-6 form-control agg-schema-type custom-select"
                  type="text"
                  optionKey="id"
                  optionValue="name"
                  noSelection="${['null':'Choose Schema...']}"
                  @change="getSchemaFields()"
                  href="${createLink(controller: 'schema', action: 'getSchemaFields')}"/>
    </div>
</div>
