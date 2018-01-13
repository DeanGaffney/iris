<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <asset:stylesheet src="iris.css" />
    <g:set var="entityName" value="${message(code: 'schema.label', default: 'Dashboards')}" />
    <title>Dashboard Home</title>
</head>
<body>

<div class="jumbotron jumbotron-fluid" style="background-image: url(${assetPath(src: 'iris/iris_jumbo_bg.png')});">
    <div class="container-fluid">
        <img class="img-fluid" src="${assetPath(src: 'iris/iris_logo_colour.png')}">
        <h1 class="display-3">Easy Data<br>Visualisation</h1>
        <button id="create-dashboard-btn" type="button" class="btn" data-toggle="modal" data-target="#create-dashboard-modal" href="${createLink(controller: 'dashboard', action: 'create')}">Create</button>
    </div>
</div>

<div id="dashboards-wrapper">
    <h1 id="dashboard-list-header">My Dashboards</h1>
    %{--<g:each in="${dashboards}" var="dashboard" status="i">--}%
        %{--<div class="row schema-row">--}%
            %{--<span class="schema-li-prefix">Schema</span>--}%
            %{--<span class="tab"></span>--}%
            %{--<span class="schema-li-id tab">${i + 1}</span>--}%
            %{--<span class="tab">-</span>--}%
            %{--<span class="schema-li-name" href="${createLink(controller: 'schema', action: 'show', params: [id: schema.id])}">${schema.name}</span>--}%
        %{--</div>--}%
    %{--</g:each>--}%
</div>

<!-- DASHBOARD CREATION MODAL -->
<div class="modal fade modal-lg" id="create-dashboard-modal">
    <div class="modal-dialog">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h5 class="modal-title">Create Dashboard</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <div class="form-group">
                    <label class="col-2 col-form-label">Name</label>
                    <div class="col-6">
                        <input class="form-control" id="create-dashboard-name" type="text" value="" required>
                    </div>
                </div>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                <g:render template="create"/>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>

        </div>
    </div>
</div>

<g:javascript>
    $("#create-schema-btn").on("click", function(){
        // var URL = $(this).attr("href");
        // updateContainerHtml(URL, REST.method.post, REST.contentType.json, {}, "#dashboard-modal-create");
        $("#create-dashboard-modal").modal({
            show: true,
            backdrop: true
        });
    });
    //
    // $(".schema-li-name").on("click", function(){
    //     var URL = $(this).attr("href");
    //     updateContainerHtml(URL, REST.method.post, REST.contentType.json, {}, "#schema-main-container");
    // });
</g:javascript>
</body>
</html>