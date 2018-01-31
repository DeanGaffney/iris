<label for="schema-field-select">Select Schema Field</label>
<g:select name="schema-field-select"
          from="${schemaFields}"
          class="col-6 form-control agg-schema-type custom-select"
          type="text"
          optionKey="id"
          optionValue="name"
          noSelection="${['null':'Choose Schema Field...']}"
          @change="getSchemaFields()"
          href="${createLink(controller: 'schema', action: 'getSchemaFields')}"/>

<g:javascript>

</g:javascript>