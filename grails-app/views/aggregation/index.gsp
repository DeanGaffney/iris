<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'aggregation.label', default: 'Aggregation')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <asset:javascript src="aggregations/aggregation.js"/>
        <asset:javascript src="aggregations/aggregation-builder.js"/>
    </head>
    <body>
      <div id="agg-main-container">
          <button id="create-agg-btn" class="btn" href="${createLink(controller: 'aggregation', action: 'create')}">Create</button>
      </div>
      <g:javascript>
            $("#create-agg-btn").on("click", function(){
               const URL =  $(this).attr("href");
               updateContainerHtml(URL, REST.method.put, REST.contentType.json, {}, "#agg-main-container");
            });
      </g:javascript>
    </body>
</html>