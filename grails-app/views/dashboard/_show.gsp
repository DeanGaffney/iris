<h1>${dashboard.name}</h1>
<div id="dashboard-container">

    <div class="form-group">
        <label for="dashboard-name">Dashboard Name</label>
        <input type="text" class="form-control" id="dashboard-name" placeholder="${dashboard.name}" required>
    </div>

    <div id="grid" class="grid-stack">

    </div>

    <div class="btn-group">
        <button id="add-widget-btn" class="btn">Add Widget</button>
        <button id="clear-dashboard-btn" class="btn">Clear</button>
        <button id="load-dashboard-btn" class="btn">Load</button>
        <button id="save-dashboard-btn" class="btn" href="${createLink(controller: 'dashboard', action: 'save')}">Save</button>
        <g:link action="onShowViewClosed" params="${[id: dashboard.id]}"
            <button id="show-close-dashboard-btn" class="btn" href="${createLink(controller: 'dashboard', action: 'onShowViewClosed')}">Close</button>
        </g:link>
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
       var url = $("#show-close-dashboard-btn").attr("href");
       var dashboardId = '${dashboard.id}'
       reloadAfterAjax(url, REST.method.post, REST.contentType.json, {dashboardId : dashboardId});
    });
</g:javascript>