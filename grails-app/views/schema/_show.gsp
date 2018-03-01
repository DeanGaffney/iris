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

                    <!--ACCORDION-->
                    <div id="accordion" role="tablist" aria-multiselectable="true">

                    <!--SCHEMA FIELDS START-->
                    <div class="card">

                        <div class="card-header" role="tab" id="schema-fields-show-header">
                            <h5 class="mb-0">
                                <a data-toggle="collapse" data-parent="#accordion" href="#schema-fields-show" aria-expanded="false" aria-controls="schema-fields-show">
                                    Schema Fields (${schema.schemaFields.size()})
                                </a>
                            </h5>
                        </div>

                        <div id="schema-fields-show" class="collapse show" role="tabpanel" aria-labelledby="schema-fields-show-header">

                            <div class="card-block">
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
                    </div>
                    <!--SCHEMA FIELDS END-->

                    <!--SCHEMA URL START-->
                    <div class="card">

                        <div class="card-header" role="tab" id="schema-url-header">
                            <h5 class="mb-0">
                                <a data-toggle="collapse" data-parent="#accordion" href="#schema-url-show" aria-expanded="false" aria-controls="schema-url-show">
                                    URI
                                </a>
                            </h5>
                        </div>

                        <div id="schema-url-show" class="collapse show" role="tabpanel" aria-labelledby="schema-url-header">
                            <div class="card-block">
                                <div id="schema-url-container">
                                    <p id="schema-url"><g:createLink controller="schema" action="route" params="[id: schema.id]" absolute="true"/></p>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!--SCHEMA URL END-->

                    <!--SCHEMA URL START-->
                    <div class="card">

                        <div class="card-header" role="tab" id="schema-json-header">
                            <h5 class="mb-0">
                                <a data-toggle="collapse" data-parent="#accordion" href="#schema-json-show" aria-expanded="false" aria-controls="schema-json-show">
                                    Expected JSON
                                </a>
                            </h5>
                        </div>

                        <div id="schema-json-show" class="collapse show" role="tabpanel" aria-labelledby="schema-json-header">
                            <div class="card-block">
                                <div id="schema-json-container">
                                    <pre>${schemaJson}</pre>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!--SCHEMA URL END-->


                    <!--SCHEMA RULE CREATION-->
                    <div class="card">

                        <div class="card-header" role="tab" id="schema-rule-header">
                            <h5 class="mb-0">
                                <a id="schema-rule-title" data-toggle="collapse" data-parent="#accordion" href="#schema-rule" aria-expanded="false" aria-controls="schema-fields">
                                    Schema Rule
                                </a>
                            </h5>
                        </div>

                        <div id="schema-rule" class="collapse show" role="tabpanel" aria-labelledby="schema-fields-header">
                            <div class="card-block">
                                <div id="schema-rule-container">
                                    <div id="editor">def groovy = 'something'</div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!--SCHEMA RULE END-->

                </div>
                    <!--ACCORDION END-->
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
</div>

<g:javascript>

    var editor = ace.edit("editor");
    editor.setTheme("ace/theme/monokai");
    editor.session.setMode("ace/mode/groovy");
    editor.session.setUseWrapMode(true);

    editor.setOptions({
        autoScrollEditorIntoView: true,
        maxLines: 100,
        readOnly: true,
        highlightActiveLine: false,
        highlightGutterLine: false
    });
    editor.renderer.setScrollMargin(10, 10, 10, 10);

    editor.setAutoScrollEditorIntoView(true);
    editor.getSession().setValue("${schema?.rule?.script}");

    $("#edit-schema-btn").on("click", function(){
        var URL = $(this).attr('href');
        updateContainerHtml(URL, REST.method.post, REST.contentType.json, {schemaId: "${schema.id}"}, "#schema-main-container");
    });

    $("#schema-modal").modal({
       show: true,
       backdrop: true
    });
</g:javascript>