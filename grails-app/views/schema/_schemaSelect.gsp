<div id="schema-area">
    <div id="schema-select-area" class="form-group">
        <g:select name="schema-select"
                  from="${com.wit.iris.schemas.Schema.list()}"
                  class="form-control agg-schema-type custom-select"
                  type="text"
                  optionKey="id"
                  optionValue="name"
                  noSelection="${['null':'Choose Schema...']}"
                  @change="getSchemaFields()"
                  href="${createLink(controller: 'schema', action: 'getSchemaFields')}"/>
    </div>
</div>
