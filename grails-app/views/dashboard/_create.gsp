<g:render template="widgetModal"/>

<div id="dashboard-container">

    <div class="form-group">
        <label for="dashboard-name">Dashboard Name</label>
        <input type="text" class="form-control" id="dashboard-name" placeholder="Dashboard Name..." required>
    </div>

    <div id="grid" class="grid-stack">

    </div>

    <div id="dashboard-btn-group" class="btn-group">
        <button id="add-widget-btn" class="btn">Add Widget</button>
        <button id="clear-dashboard-btn" class="btn">Clear</button>
        <button id="save-dashboard-btn" class="btn" href="${createLink(controller: 'dashboard', action: 'save')}">Save</button>
    </div>
</div>



<g:javascript>
        init();

        //add create class to this, so i know it's the show view
        $("#overlay-close-button").removeClass('show-view');
        $("#overlay-close-button").addClass('create-view');

        $("#overlay-close-button").on("click", function(){
            if($(this).hasClass('create-view')){
                closeOverlay();
            }
        });
</g:javascript>