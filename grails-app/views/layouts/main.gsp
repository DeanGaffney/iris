<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>

    <g:layoutHead/>
</head>
<body>
%{--NAVBAR--}%
<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            %{--<li class="nav-item">--}%
                %{--<i class="fa fa-bars fa-2x" aria-hidden="true"></i>--}%
            %{--</li>--}%
            <li class="nav-item">
                <a href="${createLink(controller:'schema', action:'index')}">Schemas</a>
            </li>
            <li class="nav-item">
                <a href="${createLink(controller:'aggregation', action:'index')}">Aggregations</a>
            </li>
            <li class="nav-item">
                <a href="${createLink(controller:'dashboard', action:'index')}">Dashboards</a>
            </li>
            <li class="nav-item">
                <a href="${createLink(controller:'elasticEndpoint', action:'index')}">Endpoints</a>
            </li>
        </ul>
    </div>
</nav>

<div id="flash-message"></div>
<g:layoutBody/>

</body>
</html>
