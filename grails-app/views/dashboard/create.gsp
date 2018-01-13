<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'dashboard.label', default: 'Dashboard')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <asset:javascript src="gridstack/gridstack.all.js"/>
    <asset:javascript src="d3/d3.min.js"/>
    <asset:javascript src="billboard/billboard.min.js"/>
    <asset:javascript src="charts/bar.js"/>
    <asset:javascript src="charts/pie.js"/>
    <asset:javascript src="dashboards/dashboard.js"/>
    <asset:javascript src="charts/placeholder-charts.js"/>
    <asset:stylesheet src="billboard/billboard.min.css"/>
    <asset:stylesheet src="gridstack/gridstack.css"/>
</head>
<body>
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
                                    <select class="form-control custom-select" id="chart-type">
                                        <option value="Bar">Bar</option>
                                        <option value="Bar">Bubble</option>
                                        <option value="Pie">Pie</option>
                                    </select>

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
<div id="dashboard-container">

    <div id="grid" class="grid-stack">

    </div>

    <button id="add-widget" class="btn">Add Widget</button>
    <button id="clear-dashboard-btn" class="btn">Clear</button>
    <button id="load-dashboard-btn" class="btn">Load</button>
    <button id="save-dashboard-btn" class="btn" href="${createLink(controller: 'dashboard', action: 'save')}" data-url="${createLink(controller: 'dashboard', action: 'show')}">Save</button>
</div>

<g:javascript>
        init();

        //clear the dashboard
        $("#clear-dashboard-btn").on("click", function(){
           clear();
        });

        //add new widget to dashboard
        $("#add-widget").on("click", function(){
            showWidgetModal();
        });

        $("#add-widget-modal-btn").on("click", function(){
            var widget = getAddedWidgetInfo();
            //close the add widget modal
            hideWidgetModal();
            //clear the add widget modal
            clearWidgetModal();
            //add widget to the dashboard
            addWidget(widget);
        });

        //save the dashboard
        $("#save-dashboard-btn").on("click", function(){
            save($(this));
        });

        //load the dashboard
        $("#load-dashboard-btn").on("click", function(){
            load();
        });

</g:javascript>
</body>
</html>