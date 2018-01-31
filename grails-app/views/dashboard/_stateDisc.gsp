<g:render template="/schema/schemaSelectWithFieldJs"/>
<div id="state-disc-form-area">

</div>
<button id="add-state-btn" class="btn" href="${createLink(controller: 'dashboard', action: 'getStateDiscForm')}">New State</button>

<g:javascript>
    $("#add-state-btn").on("click", function(){
        var url = $(this).attr("href");
        updateContainerHtml(url, REST.method.post, REST.contentType.json, {}, "#state-disc-form-area");
    });
</g:javascript>