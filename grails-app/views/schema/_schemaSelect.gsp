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

<g:javascript>

    var schemaSelectApp = new Vue({
        el: "#schema-area",
        data: {},
        methods: {
            getSchemaFields: function () {
                var url = $("#schema-select").attr("href");
                var schemaId = $("#schema-select").val();
                appendContainerHtml(url, REST.method.post, REST.contentType.json, {schemaId: schemaId}, "#schema-area");
            }
        }
    });

</g:javascript>