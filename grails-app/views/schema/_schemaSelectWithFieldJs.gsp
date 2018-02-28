<g:render template="/schema/schemaSelect"/>
<div id="schema-field-area"></div>
<g:javascript>

    var schemaSelectApp = new Vue({
        el: "#schema-area",
        data: {},
        methods: {
            getSchemaFields: function () {
                var url = $("#schema-select").attr("href");
                var schemaId = $("#schema-select").val();
                updateContainerHtml(url, REST.method.post, REST.contentType.json, {schemaId: schemaId}, "#schema-field-area");
            },
            appendSchemaFields: function(){
                var url = $("#schema-select").attr("href");
                var schemaId = $("#schema-select").val();
                appendContainerHtml(url, REST.method.post, REST.contentType.json, {schemaId: schemaId}, "#schema-field-area");
            }
        }
    });

    $(document).on("change", "#schema-select", function(){
       schemaSelectApp.getSchemaFields();
    });
</g:javascript>