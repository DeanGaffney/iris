<g:select name="schema-field-select"
          from="${schemaFields}"
          class="form-control agg-schema-type custom-select"
          type="text"
          optionKey="id"
          optionValue="name"
          noSelection="${['null':'Choose Schema Field...']}"
          @change="getSchemaFields()"
          href="${createLink(controller: 'schema', action: 'getSchemaFields')}"/>

<g:javascript>

</g:javascript>