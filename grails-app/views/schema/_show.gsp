<div id="show-schema-container">
    <div id="schema-modal" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">${schema.name}</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div id="schema-field-collapse" data-toggle="collapse" href="#schema-fields-container" aria-expanded="false" aria-controls="schema-fields-container">
                        <h5>Schema Fields</h5>
                        <div id="schema-fields-container">
                            <table id="schema-fields-table" class="table">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Name</th>
                                        <th>Type</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <g:each in="${schema.schemaFields}" var="schemaField" status="i">
                                        <tr>
                                            <th scope="row">${i + 1}</th>
                                            <td>${schemaField.name}</td>
                                            <td>${schemaField.fieldType}</td>
                                        </tr>
                                    </g:each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div id="schema-url" data-toggle="collapse" href="#schema-url-container" aria-expanded="false" aria-controls="schema-url-container">
                    <h5>URL</h5>
                    <div id="schema-url-container">
                        <p><g:createLink controller="schema" action="route" params="[id: schema.id]" absolute="true"/></p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="edit-schema-btn" type="button" class="btn" href="${createLink(controller: 'schema', action: 'edit')}">Edit</button>
                    <g:link action="delete" params="${[id: schema.id]}">
                        <button id="delete-schema-btn" type="button" class="btn">Delete</button>
                    </g:link>
                </div>
            </div>
        </div>
    </div>
    <div class="row">

    </div>
</div>

<g:javascript>
    $("#edit-schema-btn").on("click", function(){
        const URL = $(this).attr('href');
        updateContainerHtml(URL, REST.method.post, REST.contentType.json, {schemaId: "${schema.id}"}, "#schema-main-container");
    });

    $("#schema-modal").modal({
       show: true,
       backdrop: true
    });
</g:javascript>