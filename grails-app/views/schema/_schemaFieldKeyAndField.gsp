<g:render template="/schema/schemaSelect"/>
<div id="schema-field-area"></div>
<g:javascript>

    var schemaSelectAppRaw = new Vue({
        el: "#schema-area",
        data: {},
        methods: {
            getSchemaFields: function () {
                var url = $("#schema-select").attr("href");
                var schemaId = $("#schema-select").val();
                rawDataContainerUpdate(url, REST.method.post, REST.contentType.json, {schemaId: schemaId}, "#schema-field-area");
            }
        }
    });

    $(document).on("change", "#schema-select", function(){
        schemaSelectAppRaw.getSchemaFields();
    });
</g:javascript>