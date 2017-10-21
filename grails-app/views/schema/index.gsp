<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'schema.label', default: 'Schema')}" />
        <title>Schema Home</title>
    </head>
    <body>
      <div id="schema-main-container" class="container-fluid">
          <button id="create-schema-btn" type="button" class="btn btn-primary" href="${createLink(controller: 'schema', action: 'create')}">Create</button>
      </div>
      <g:javascript>
        $("#create-schema-btn").on("click", function(){
            const URL = $(this).attr("href");
            updateContainerHtml(URL, REST.method.post, REST.contentType.json, {}, "#schema-main-container");
        });
      </g:javascript>
    </body>
</html>