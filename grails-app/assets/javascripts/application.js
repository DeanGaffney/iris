// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better
// to create separate JavaScript files as needed.
//

//= require jquery/jquery-3.2.1.min.js
//= require jquery/jquery-ui.min.js
//= require bootstrap/tether.min.js
//= require bootstrap/bootstrap.min.js
//= require flash-messages/flash.js
//= require lodash/lodash.min.js
//= require_tree .
//= require_self

if (typeof jQuery !== 'undefined') {
    (function($) {
        $(document).ajaxStart(function() {
            $('#spinner').fadeIn();
        }).ajaxStop(function() {
            $('#spinner').fadeOut();
        });
    })(jQuery);
}

var REST = {
    method:{
        get: "get",
        post: "post",
        put: "put"
    },
    dataType:{
        json: "json",
        html: "html",
        text: "text"
    },
    contentType:{
        json:"application/json; charset=utf-8"
    }
};

function reloadAfterAjax(controllerUrl, methodType, contentType, data){
    $.ajax({
        url: controllerUrl,
        type: methodType,
        dataType: REST.dataType.html,
        contentType: contentType,
        data: JSON.stringify(data),
        success: function(){
            location.reload(true);
        },
        error: function(xhr, status, error) {
            console.log(xhr.responseText);
        }
    });
}

function toggleServerObjectState(controllerUrl, methodType, contentType, data){
    $.ajax({
        url: controllerUrl,
        type: methodType,
        dataType: REST.dataType.html,
        contentType: contentType,
        data: JSON.stringify(data),
        success: function(){

        },
        error: function(xhr, status, error) {
            console.log(xhr.responseText);
        }
    });
}

function prettyPrintJsonResponse(controllerUrl, methodType, contentType, data, successContainer){
    $.ajax({
        url: controllerUrl,
        type: methodType,
        dataType: REST.dataType.html,
        contentType: contentType,
        data: JSON.stringify(data),
        success: function(data){
            $(successContainer).html("<h1>Result</h1><pre>" + JSON.stringify(JSON.parse(data), null, 4) + "</pre>");
        },
        error: function(xhr, status, error) {
            console.log(xhr.responseText);
        }
    });
}

function updateContainerHtml(controllerUrl, methodType, contentType, data, successContainer){
    $.ajax({
        url: controllerUrl,
        type: methodType,
        dataType: REST.dataType.html,
        contentType: contentType,
        data: JSON.stringify(data),
        success: function(data){
            $(successContainer).html(data);
        },
        error: function(xhr, status, error) {
            console.log(xhr.responseText);
        }
    });
}

function appendContainerHtml(controllerUrl, methodType, contentType, data, successContainer){
    $.ajax({
        url: controllerUrl,
        type: methodType,
        dataType: REST.dataType.html,
        contentType: contentType,
        data: JSON.stringify(data),
        success: function(data){
            $(successContainer).append(data);
        },
        error: function(xhr, status, error) {
            console.log(xhr.responseText);
        }
    });
}

function rawDataContainerUpdate(controllerUrl, methodType, contentType, data, successContainer){
    $.ajax({
        url: controllerUrl,
        type: methodType,
        dataType: REST.dataType.html,
        contentType: contentType,
        data: JSON.stringify(data),
        success: function(data){
            $(successContainer).html(data);
            $(successContainer).append(data);
        },
        error: function(xhr, status, error) {
            console.log(xhr.responseText);
        }
    });
}

