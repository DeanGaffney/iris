<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <asset:stylesheet src="iris.css" />
        <g:set var="entityName" value="${message(code: 'schema.label', default: 'Schema')}" />
        <title>Schema Home</title>
    </head>
    <body>
        <div class="jumbotron jumbotron-fluid" style="background-image: url(${assetPath(src: 'iris/iris_jumbo_bg.png')});">
            <div class="container-fluid">
                <img class="img-fluid" src="${assetPath(src: 'iris/iris_logo_colour.png')}">
                <h1 class="display-3">Easy Data<br>Visualisation</h1>
                <button id="create-schema-btn" type="button" class="btn" href="${createLink(controller: 'schema', action: 'create')}">Create</button>
            </div>
        </div>
        <div id="schemas-wrapper">
            <h1 id="schema-list-header">My Schemas</h1>
            <g:each in="${schemas}" var="schema" status="i">
                <div class="row schema-row">
                    <span class="schema-li-num">Schema</span>
                    <span class="tab"></span>
                    <span class="schema-li-num tab">${i + 1}</span>
                    <span class="tab">-</span>
                    <span class="schema-li-name">${schema.name}</span>
                </div>
            </g:each>
        </div>
          <div id="schema-main-container" class="container-fluid"></div>
          <g:javascript>
            $("#create-schema-btn").on("click", function(){
                const URL = $(this).attr("href");
                updateContainerHtml(URL, REST.method.post, REST.contentType.json, {}, "#schema-main-container");
            });
          </g:javascript>
    </body>
</html>