<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'dashboard.label', default: 'Dashboard')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <asset:javascript src="spring-websocket"/>
    <asset:javascript src="gridstack/gridstack.all.js"/>
    <asset:javascript src="billboard/billboard.pkgd.min.js"/>
    <asset:stylesheet src="billboard/billboard.min.css"/>
    <asset:javascript src="charts/charts.js"/>
    <asset:javascript src="charts/subscription-charts.js"/>
    <asset:javascript src="charts/placeholder-charts.js"/>
    <asset:javascript src="dashboards/dashboard.js"/>
    <asset:stylesheet src="gridstack/gridstack.css"/>
    <asset:stylesheet src="gridstack/gridstack-extra.css"/>
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

<div id="dashboards-wrapper" class="list-wrapper">
    <h1 id="dashboard-list-header" class="list-header">My Dashboards</h1>
    <g:each in="${dashboards}" var="dashboard" status="i">
        <div class="row list-item-row">
            <span class="list-item-prefix">Dashboard</span>
            <span class="tab"></span>
            <span class="list-item-id tab">${i + 1}</span>
            <span class="tab">-</span>
            <span class="list-item-name" href="${createLink(controller: 'dashboard', action: 'show', params: [revisionId: dashboard.revision.revisionId, revisionNumber: dashboard.revision.revisionNumber])}">${dashboard.name}</span>
        </div>
    </g:each>
</div>

<div id="dashboard-area" class="overlay">
    <span><i id="overlay-close-button" class="fa fa-times fa-2x" href="${createLink(controller: 'dashboard', action: 'onShowViewClosing')}"></i></span>
    <div class="overlay-content"></div>
</div>

<g:javascript>

    var socket = new SockJS("${createLink(uri: '/stomp')}");
    var client = Stomp.over(socket);

    client.connect({}, function(){});
    client.debug = null;


    //show creation area when create button is clicked
    $("#create-dashboard-btn").on("click", function(){
        var URL = $(this).attr("href");
        updateContainerHtml(URL, REST.method.post, REST.contentType.json, {}, ".overlay-content");
        openOverlay();
    });

    /* Open when someone clicks on the span element */
    function openOverlay() {
       $("#dashboard-area").width("100%");
    }

    /* Close when someone clicks on the "x" symbol inside the overlay */
    function closeOverlay() {
        $("#dashboard-area").width("0%");
    }

    // show dashboard when clicked on
    $(".list-item-name").on("click", function(){
        var URL = $(this).attr("href");
        updateContainerHtml(URL, REST.method.post, REST.contentType.json, {}, ".overlay-content");
        openOverlay();
    });

    //get widget that was clicked to be removed
    $(document).on('click','.widget-remove',function(e) {
        var widget = $(this).closest('.grid-stack-item');
        removeWidget(widget);
    });

        //get widget that was clicked to be downloaded as image
    $(document).on('click','.widget-img-download',function(e) {
        var widget = $(this).closest('.grid-stack-item');
        downloadChartImage(widget);
    });

        //get widget that was cliked to be downloaded as json file
    $(document).on('click','.widget-json-download',function(e) {
        var widget = $(this).closest('.grid-stack-item');
        downloadChartJson(widget);
    });

     //clear the dashboard
    $(document).on("click", "#clear-dashboard-btn", function(){
       clear();
    });

    //add new widget to dashboard
    $(document).on("click", "#add-widget-btn",  function(){
        showWidgetModal();
    });

    $(document).on("click", "#add-widget-modal-btn", function(){
        var widget = getAddedWidgetInfo();
        //close the add widget modal
        hideWidgetModal();
        //clear the add widget modal
        clearWidgetModal();
        //add widget to the dashboard
        addWidget(widget, false);
    });

    //save the dashboard
    $(document).on("click", "#save-dashboard-btn", function(){
        save($(this).attr('href'));
    });

    //if a resize occurs, then resize the chart to be the width and height of the new widget size
    $(document).on('gsresizestop', '.grid-stack', function(event, elem) {
        var elemHeight = $(elem).height();
        var newHeight = elemHeight - (elemHeight * 0.2);      //subtract 20%
        //update chart height, billboard dynamically does width for chart
        var widgetId = $(elem).attr('id');
        var chart = displayingCharts[widgetId];
        updateChartHeight(chart, newHeight);
        resizeWidget('#' + widgetId);
    });

    $(window).bind('load', function(){
        if(localStorage.getItem('dashboard-revision') !== null){
            var data = JSON.parse(localStorage.getItem('dashboard-revision'));
            var url = $("#overlay-close-button").attr("href");
            toggleServerObjectState(url, REST.method.post, REST.contentType.json, data);
            localStorage.removeItem('dashboard-revision');
        }
    });

</g:javascript>
</body>
</html>