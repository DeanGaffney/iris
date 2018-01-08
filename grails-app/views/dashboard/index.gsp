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
        <g:link controller="dashboard" action="create">
            <button id="create-dashboard-btn" type="button" class="btn" href="${createLink(controller: 'dashboard', action: 'create')}">Create</button>
        </g:link>
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
<g:javascript>
    // $("#create-schema-btn").on("click", function(){
    //     var URL = $(this).attr("href");
    //     updateContainerHtml(URL, REST.method.post, REST.contentType.json, {}, "#schema-main-container");
    // });
    //
    // $(".schema-li-name").on("click", function(){
    //     var URL = $(this).attr("href");
    //     updateContainerHtml(URL, REST.method.post, REST.contentType.json, {}, "#schema-main-container");
    // });
</g:javascript>
</body>
</html>