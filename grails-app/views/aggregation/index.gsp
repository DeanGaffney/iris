<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'aggregation.label', default: 'Aggregation')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <asset:javascript src="vue/vue.js"/>

        <asset:javascript src="aggregations/aggregation.js"/>
        <asset:javascript src="aggregations/aggregation-builder.js"/>

    </head>
    <body>
      <div id="agg-main-container">
          <g:render template="create"/>
      </div>
    </body>
</html>