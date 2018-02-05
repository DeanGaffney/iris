<div id="create-elastic-container">
    <div id="elastic-modal" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <div class="modal-header">
                    <h5 class="modal-title">Create Elasticsearch Endpoint</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">

                    <div id="accordion" role="tablist" aria-multiselectable="true">

                        <!--ELASTIC ENDPOINT CREATION START-->
                        <div class="card">

                            <div class="card-header" role="tab" id="elastic-endpoint-header">
                                <h5 class="mb-0">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#elastic-endpoint-creation" aria-expanded="false" aria-controls="elastic-endpoint-creation">
                                        Elasticsearch Endpoint
                                    </a>
                                </h5>
                            </div>

                            <div id="elastic-endpoint-creation" class="collapse show" role="tabpanel" aria-labelledby="elastic-endpoint-header">
                                <div class="card-block">

                                    <div class="form-group">
                                        <label class="col-2 col-form-label">Name</label>
                                        <div class="col-6">
                                            <input class="form-control" id="elastic-endpoint-name" type="text" value="" required>
                                        </div>
                                        <label class="col-2 col-form-label">Endpoint</label>
                                        <div class="col-6">
                                            <input class="form-control" id="elastic-endpoint-url" type="text" value="" required>
                                        </div>
                                        <label class="col-6 col-form-label">Active
                                            <div class="col-4">
                                                <input class="form-check-input" id="elastic-endpoint-state" type="checkbox" value="" required>
                                            </div>
                                        </label>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <!--ELASTIC ENDPOINT CREATION END-->

                    </div>
                    <!--ACCORDION END-->
                </div>

                <div class="modal-footer">
                    <button type="button" id="save-elastic-btn" class="btn ml-1" href="${createLink(controller: 'elasticEndpoint', action: 'save')}">Save</button>
                </div>

            </div>
        </div>
    </div>
</div>

<g:javascript>

    $("#elastic-modal").modal({
        show: true,
        backdrop: true
    });

    $("#save-elastic-btn").on("click", function(){
        var URL = $(this).attr("href");
        var endpointName = $("#elastic-endpoint-name").val();
        var endpointUrl = $("#elastic-endpoint-url").val();
        var state = $("#elastic-endpoint-state").is(":checked");

        var data = {
            name: endpointName,
            url: endpointUrl,
            active: state
        }

        reloadAfterAjax(URL, REST.method.post, REST.contentType.json, data);
    });
</g:javascript>