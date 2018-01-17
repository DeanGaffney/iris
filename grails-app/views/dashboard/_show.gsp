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
        <button id="close-dashboard-btn" class="btn" onclick="closeNav()">Close</button>
        <g:link action="delete" params="${[id: dashboard.id]}">
            <button id="delete-dashboard-btn" type="button" class="btn">Delete</button>
        </g:link>
    </div>

</div>

<g:javascript>
    init();
    var jsonStr = '${serializedData.toString()}';
    var loadedDashboard = JSON.parse(jsonStr);
    load(loadedDashboard.serializedData);
</g:javascript>