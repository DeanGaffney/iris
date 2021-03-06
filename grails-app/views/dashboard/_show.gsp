<g:render template="widgetModal"/>

<!--REVISION COMMENT MODAL-->
<div id="revision-comment-modal" class="modal fade">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Revision Comment</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="revision-comment-textarea">Example textarea</label>
                    <textarea class="form-control" id="revision-comment-textarea" rows="3"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button id="commit-revision-btn" type="button" class="btn">Commit changes</button>
                <button type="button" class="btn" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<!--DASHBOARD AREA-->
<div id="dashboard-header-area" class="row">
    <div class="col-3">
        <h1>Dashboard Name:</h1>
        <h1 id="dashboard-name-header">${dashboard.name}</h1>
    </div>
    <div class="col-6">
        <h1>Revision Comment:</h1>
        <h5 id="revision-comment">"${dashboard.revision?.comment}"</h5>
    </div>
    <div class="col-3">
        <h1>Revision No: ${revisionNumber}</h1>
        <g:select name="dashboard-revision-select" from="${revisions}"
                  optionValue="${{'Rev:(' + it.revisionNumber + ') ' + it.dateCreated}}"
                  optionKey="${{it.revisionNumber}}"
                  noSelection="[revisionNumber:'Rev:(' + revisionNumber + ')']"
                  class="form-control custom-select"
                  href="${createLink(controller: 'dashboard', action: 'onRevisionChange')}"/>
    </div>
</div>


<div id="dashboard-container">

    <div id="grid" class="grid-stack"></div>

    <div id="dashboard-btn-group" class="btn-group">
        <button id="add-widget-btn" class="btn">Add Widget</button>
        <button id="clear-dashboard-btn" class="btn">Clear</button>
        <button id="update-dashboard-btn" class="btn" href="${createLink(controller: 'dashboard', action: 'update')}" disabled>Update</button>
        <button id="delete-dashboard-btn" type="button" class="btn" href="${createLink(controller: 'dashboard', action: 'delete')}">Delete</button>
    </div>

</div>

<g:javascript>

    $("#overlay-close-button").removeClass('create-view');
    $("#overlay-close-button").addClass('show-view');

    init();
    var onDashboardChartLoadUrl = '${createLink(controller: 'dashboard', action: 'onDashboardChartLoad')}'
    var dashboardRevisionId = '${dashboard.revision.revisionId}';
    var dashboardRevisionNumber = '${revisionNumber}';
    var data = {
        dashboardRevisionId: dashboardRevisionId,
        dashboardRevisionNumber: dashboardRevisionNumber
    };
    var jsonStr = '${serializedData.toString()}';
    var loadedDashboard = JSON.parse(jsonStr);
    load(onDashboardChartLoadUrl, data, loadedDashboard.serializedData);

    //if user closes tab/browser or refreshes page, we need to toggle dashboard as not rendering anymore
    $(window).bind('beforeunload', function(){
       //before the page reloads, add the dashboard object to local storage
       localStorage.setItem('dashboard-revision', JSON.stringify(data));
    });

    //if the overlay close button is clicked in the show view, trigger the beforeunload event to toggle dashboard rendering state server side
    $("#overlay-close-button").on("click", function(){
        if($(this).hasClass('show-view')){
            location.reload(true);
        }
    });

    $('.grid-stack').on('change', function(event, items) {
        $("#update-dashboard-btn").prop('disabled', false);
    });

    $("#update-dashboard-btn").on("click", function(){
        //show modal
        $("#revision-comment-modal").modal({
            show: true,
            backdrop: true
        });
    });

    $("#commit-revision-btn").on("click", function(){
        var dashboardRevisionComment = $("#revision-comment-textarea").val();
        update($("#update-dashboard-btn").attr('href'), dashboardRevisionId, dashboardRevisionNumber, dashboardRevisionComment);
    });

    $("#dashboard-revision-select").on("change", function(){
        var url = $(this).attr("href");
        var revisionData = data;
        revisionData['requestedRevisionNumber'] = $(this).val();
        updateContainerHtml(url, REST.method.post, REST.contentType.json, revisionData, '.overlay-content');
    });

    $("#delete-dashboard-btn").on("click", function(){
        //delete revision
        //if revision has more than 2 options??? make updateContainer call
        //else make reloadAfterAjax call
        var url = $(this).attr("href");
        var revisionData = data;
        revisionData.reload = false;
        if($("#dashboard-revision-select > option").length > 1){
            updateContainerHtml(url, REST.method.post, REST.contentType.json, revisionData, ".overlay-content");
        }else{
            revisionData.reload = true;
            reloadAfterAjax(url, REST.method.post, REST.contentType.json, revisionData);
        }
    });

</g:javascript>