<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <asset:javascript src="application.js"/>
        <asset:stylesheet src="application.css"/>
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

            <div class="schema-form">
                <h2>Schema</h2>
                <div class="form-group">
                    <label class="col-2 col-form-label">Name</label>
                    <div class="col-6">
                        <input class="form-control" id="schema-name" required="" type="text" value="" >
                    </div>
                    <label class="col-2 col-form-label">Refresh Interval</label>
                    <div class="col-6">
                        <input class="form-control" id="schema-refresh" required="" type="number" value="" >
                    </div>
                </div>
                <div id="schema-field-container"></div>
            </div>
            <button type="button" id="add-schema-field-btn" class="btn btn-primary">Add field</button>
            <button type="button" id="save-schema-btn" class="btn btn-primary">Save</button>

        </div>
    <g:javascript>

        var schema = function(name, refreshInterval){
            this.name = name;
            this.refreshInterval = refreshInterval;
            this.schemaFields = [];
        }

        var schemaField = function(name, fieldType){
            this.name = name;
            this.fieldType = fieldType;
        }

        $("#add-schema-field-btn").on( "click", function(){
            const URL = "${createLink(controller: 'schemaField', action: 'form')}";
           $.ajax({
               url: URL,
               type: "post",
               dataType: "text",
               success: function(data){
                   $("#schema-field-container").append(data);
               }
           });
        });

        $("#save-schema-btn").on("click", function(){
            const URL = "${createLink(controller: 'schema', action: 'save')}";
            //create schema object from name and refresh interval
            var schemaObj = new schema($("#schema-name").val(), $("#schema-refresh").val());
            $(".schema-field-form").each(function(){
                var schemaFieldObj = new schemaField($(this).find(".schema-field-name").val(), $(this).find(".schema-field-type").val());
                //add this obj to schema array
                schemaObj.schemaFields.push(schemaFieldObj);
            });

            //send schema obj to save action in schema controller
            $.ajax({
                url: URL,
                type: "post",
                data: JSON.stringify(schemaObj),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function(data){
                    displayFlashMessage(data.flashType, data.message);
                }
            });
        });
    </g:javascript>
    </body>
</html>
