<div id="show-schema-container">
    ${schema.name}
    <div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="card" style="width: 20rem;">
                    <div class="card-block">
                        <h4 class="card-title">${schema.name}</h4>
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">${schema.esIndex}</li>
                        <li class="list-group-item">${schema.refreshInterval}</li>
                        <li class="list-group-item">
                            SchemaFields
                            <ul class="list-group list-group-flush">
                            <g:each in="${(schema?.schemaFields).toList()}" var="schemaField" >
                                <li class="list-group-item">
                                    <span>${schemaField.name}</span>
                                    <span>${schemaField.fieldType}</span>
                                </li>
                            </g:each>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <button id="edit-schema-btn" type="button" class="btn btn-primary" href="${createLink(controller: 'schema', action: 'edit')}">Edit</button>
        <g:link action="delete" params="${[id: schema.id]}">
            <button id="delete-schema-btn" type="button" class="btn btn-primary">Delete</button>
        </g:link>
    </div>
</div>

<g:javascript>
    $("#edit-schema-btn").on("click", function(){
        const URL = $(this).attr('href');
        updateContainerHtml(URL, REST.method.post, REST.contentType.json, {schemaId: "${schema.id}"}, "#schema-main-container");
    });

    %{--$("#delete-schema-btn").on("click", function(){--}%
        %{--const URL = $(this).attr('href');--}%
        %{--updateContainerHtml(URL, REST.method.post, REST.contentType.json, {schemaId: "${schema.id}"}, "#schema-main-container");--}%
    %{--});--}%
</g:javascript>