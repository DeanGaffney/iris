<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'schema.label', default: 'Schema')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-schema" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-schema" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.schema}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.schema}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form id="schema-form" resource="${this.schema}" method="POST">
                <f:field property="name" />
                <f:field property="refreshInterval"/>
                <div id="add-field-btn" class="btn">Add Field</div>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div>
    <script type="text/javascript">
        $("#add-field-btn").click(function(){
            const URL = "${createLink(controller: 'schemaField', action: 'form')}";
            var numOfSchemaFieldForms = $(".schemaField-form").length ;
            var schemaFieldIndex = (numOfSchemaFieldForms - 1 < 0) ? 0 : numOfSchemaFieldForms - 1;
           $.ajax({
               url: URL,
               type: "post",
               dataType: "text",
               data: {index: schemaFieldIndex},
               success: function(data){
                   $("#schema-form").append(data);
               }
           });
        });
    </script>
    </body>
</html>
