<g:render template="/schema/schemaSelect"/>
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