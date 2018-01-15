<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'dashboard.label', default: 'Dashboard')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <asset:javascript src="gridstack/gridstack.all.js"/>
    <asset:javascript src="d3/d3.min.js"/>
    <asset:stylesheet src="billboard/billboard.min.css"/>
    <asset:javascript src="billboard/billboard.min.js"/>
    <asset:javascript src="charts/bar.js"/>
    <asset:javascript src="charts/pie.js"/>
    <asset:javascript src="dashboards/dashboard.js"/>
    <asset:javascript src="charts/placeholder-charts.js"/>
    <asset:stylesheet src="gridstack/gridstack.css"/>
    <asset:javascript src="bootstrap/bs-modal-fullscreen.min.js"/>
    <g:set var="entityName" value="${message(code: 'schema.label', default: 'Dashboards')}" />
    <title>Dashboard Home</title>
</head>
<body>

<div class="jumbotron jumbotron-fluid" style="background-image: url(${assetPath(src: 'iris/iris_jumbo_bg.png')});">
    <div class="container-fluid">
        <img class="img-fluid" src="${assetPath(src: 'iris/iris_logo_colour.png')}">
        <h1 class="display-3">Easy Data<br>Visualisation</h1>
        <button id="create-dashboard-btn" type="button" class="btn" href="${createLink(controller: 'dashboard', action: 'create')}">Create</button>
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

<div id="dashboard-area" class="overlay">
    <div class="overlay-content">
        <g:render template="create" />
    </div>
</div>

<g:javascript>
    $("#create-dashboard-btn").on("click", function(){
        // var URL = $(this).attr("href");
        // updateContainerHtml(URL, REST.method.post, REST.contentType.json, {}, "#dashboard-creation-area");
        openNav();
    });

    /* Open when someone clicks on the span element */
    function openNav() {
       $("#dashboard-area").width("100%");
    }

    /* Close when someone clicks on the "x" symbol inside the overlay */
    function closeNav() {
        $("#dashboard-area").width("0%");
    }
    //
    // $(".schema-li-name").on("click", function(){
    //     var URL = $(this).attr("href");
    //     updateContainerHtml(URL, REST.method.post, REST.contentType.json, {}, "#schema-main-container");
    // });
</g:javascript>
</body>
</html>