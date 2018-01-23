<div id="widget-modal" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title">Add Widget</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body">

                <!--ACCORDION-->
                <div id="accordion" role="tablist" aria-multiselectable="true">

                    <!--SCHEMA SELECTION-->

                    <div class="card">

                        <div class="card-header" role="tab" id="schema-heading">
                            <h5 class="mb-0">
                                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#schema-selection" aria-expanded="true" aria-controls="schema-selection">
                                    Schema
                                </a>
                            </h5>
                        </div>

                        <div id="schema-selection" class="collapse" role="tabpanel" aria-labelledby="schema-heading">
                            <div class="card-block">
                                <div class="form-group">
                                    <g:select name="schema-select"
                                              from="${com.wit.iris.schemas.Schema.list()}"
                                              class="form-control agg-schema-type custom-select"
                                              type="text"
                                              optionKey="id"
                                              optionValue="name"
                                              noSelection="${['null':'Choose Schema...']}"/>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!--SCHEMA SELECTION END-->


                    <!--CHART CREATION-->
                    <div class="card">

                        <div class="card-header" role="tab" id="chart-creation-header">
                            <h5 class="mb-0">
                                <a data-toggle="collapse" data-parent="#accordion" href="#chart-creation" aria-expanded="false" aria-controls="chart-creation">
                                    Chart
                                </a>
                            </h5>
                        </div>

                        <div id="chart-creation" class="collapse show" role="tabpanel" aria-labelledby="chart-heading">
                            <div class="card-block">
                                <div class="form-group">

                                    <label for="chart-name">Chart Name</label>
                                    <input type="text" class="form-control" id="chart-name" placeholder="Name...">

                                    <label for="chart-type">Example multiple select</label>

                                    <g:select name="chart-type" from="${com.wit.iris.charts.enums.ChartType.values()*.getValue()}"
                                              keys="${com.wit.iris.charts.enums.ChartType.values()*.getValue()}"
                                              class="form-control custom-select"/>


                                </div>
                            </div>
                        </div>
                    </div>
                    <!--CHART CREATION END-->

                    <!--AGGREGATION CREATION-->
                    <div class="card">

                        <div class="card-header" role="tab" id="aggregation-header">
                            <h5 class="mb-0">
                                <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#aggregation-creation" aria-expanded="false" aria-controls="aggregation-creation">
                                    Aggregation
                                </a>
                            </h5>
                        </div>

                        <div id="aggregation-creation" class="collapse" role="tabpanel" aria-labelledby="aggregation-header">
                            <div class="card-block">
                                <div class="form-group">
                                    <label for="aggregation-text-area">Aggregation Text Area</label>
                                    <textarea class="form-control" id="aggregation-text-area"></textarea>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!--AGGREGATION CREATION END-->

                </div>
            </div>

            <div class="modal-footer">
                <button type="button" id="add-widget-modal-btn" class="btn ml-1">Add</button>
            </div>

        </div>
    </div>
</div>

<span><i id="overlay-close-button" class="fa fa-times fa-2x" href="${createLink(controller: 'dashboard', action: 'onShowViewClosed')}"></i></span>

<h1>${dashboard.name}</h1>

<div id="dashboard-container">

    <div class="form-group">
        <label for="dashboard-name">Dashboard Name</label>
        <input type="text" class="form-control" id="dashboard-name" placeholder="${dashboard.name}" required>
    </div>

    <div id="grid" class="grid-stack">

    </div>

    <div id="dashboard-btn-group" class="btn-group">
        <button id="add-widget-btn" class="btn">Add Widget</button>
        <button id="clear-dashboard-btn" class="btn">Clear</button>
        <button id="save-dashboard-btn" class="btn" href="${createLink(controller: 'dashboard', action: 'save')}" disabled>Save</button>
        <g:link action="delete" params="${[id: dashboard.id]}">
            <button id="delete-dashboard-btn" type="button" class="btn">Delete</button>
        </g:link>
    </div>

</div>

<g:javascript>
    init();
    var onDashboardChartLoadUrl = '${createLink(controller: 'dashboard', action: 'onDashboardChartLoad')}'
    var dashboardId = '${dashboard.id}';
    var data = {dashboardId: dashboardId};
    var jsonStr = '${serializedData.toString()}';
    var loadedDashboard = JSON.parse(jsonStr);
    load(onDashboardChartLoadUrl, data, loadedDashboard.serializedData);

    //if user closes tab/browser or refreshes page, we need to toggle dashboard as not rendering anymore
    $(window).bind('beforeunload', function(){
       var url = $("#overlay-close-button").attr("href");
       var data = {dashboardId: dashboardId};
       reloadAfterAjax(url, REST.method.post, REST.contentType.json, data);
    });

    $("#overlay-close-button").on("click", function(){
       $(window).trigger('beforeunload');
    });

    $('.grid-stack').on('change', function(event, items) {
        $("#save-dashboard-btn").prop('disabled', false);
    });

</g:javascript>