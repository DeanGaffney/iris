<div id="show-elastic-container">
    <div id="elastic-modal" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <div class="modal-header">
                    <h5 class="modal-title">${endpoint.name}</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">

                    <!--ACCORDION-->
                    <div id="accordion" role="tablist" aria-multiselectable="true">

                        <!--ENDPOINT URL START-->
                        <div class="card">

                            <div class="card-header" role="tab" id="endpoint-url-header">
                                <h5 class="mb-0">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#elastic-url-show" aria-expanded="false" aria-controls="elastic-url-show">
                                        URL
                                    </a>
                                </h5>
                            </div>

                            <div id="elastic-url-show" class="collapse show" role="tabpanel" aria-labelledby="elastic-url-header">

                                <div class="card-block">
                                    <div id="elastic-url-container">
                                        <p id="elastic-url">${endpoint.url}</p>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <!--ENDPOINT URL END-->

                        <!--ENDPOINT STATE START-->
                        <div class="card">

                            <div class="card-header" role="tab" id="elastic-state-header">
                                <h5 class="mb-0">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#elastic-state-show" aria-expanded="false" aria-controls="elastic-state-show">
                                        State
                                    </a>
                                </h5>
                            </div>

                            <div id="elastic-state-show" class="collapse show" role="tabpanel" aria-labelledby="elastic-state-header">
                                <div class="card-block">
                                    <div id="elastic-state-container">
                                        <p id="elastic-state">Active: ${Boolean.valueOf(endpoint.active)}</p>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <!--ENDPOINT STATE END-->

                    </div>
                    <!--ACCORDION END-->
                </div>

                <div class="modal-footer">
                    <g:link action="delete" params="${[id: endpoint.id]}">
                        <button id="delete-elastic-btn" type="button" class="btn">Delete</button>
                    </g:link>
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
</g:javascript>