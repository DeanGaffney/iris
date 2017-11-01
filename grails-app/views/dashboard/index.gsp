<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'dashboard.label', default: 'Dashboard')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <asset:javascript src="lodash/lodash.min.js"/>
        <asset:javascript src="gridstack/gridstack.js"/>
        <asset:javascript src="gridstack/gridstack.jQueryUI.js"/>
        <asset:javascript src="d3/d3.min.js"/>
        <asset:javascript src="billboard/billboard.min.js"/>
        <asset:stylesheet src="billboard/billboard.min.css"/>
        <asset:stylesheet src="gridstack/gridstack.css" />
    </head>
    <body>
        <div id="dashboard-container">

            <div id="grid" class="grid-stack">

                <div class="chart-container"
                     data-gs-x="0" data-gs-y="0"
                     data-gs-width="4" data-gs-height="2">
                    <div class="grid-stack-item-content">
                        <div id="chart"></div>
                    </div>
                </div>

                <div class="chart-container"
                     data-gs-x="4" data-gs-y="0"
                     data-gs-width="4" data-gs-height="4">
                    <div class="grid-stack-item-content">ITEM 2</div>
                </div>

            </div>
        </div>

        <g:javascript>
            $(function(){
                //gridstack stuff
                $('.grid-stack').gridstack({
                    resizable: {
                        handles: 'e, se, s, sw, w'
                    }
                });
                //grab the grid
                var grid = $("#grid").data('gridstack');

                //turn all elements into widgets
                $(".chart-container").each(function(){
                    grid.makeWidget($(this));
                });

                //billboard stuff
                var chart = bb.generate({
                    bindto: "#chart",
                    data: {
                        type: "bar",
                        columns: [
                            ["data1", 30, 200, 100, 170, 150, 250],
                            ["data2", 130, 100, 140, 35, 110, 50]
                        ]
                    }
                });

                grid.resize(
                        $('.grid-stack-item')[0],
                        $($('.grid-stack-item')[0]).attr('data-gs-width'),
                        Math.ceil(($('.grid-stack-item-content')[0].scrollHeight + $('.grid-stack').data('gridstack').opts.verticalMargin) / ($('.grid-stack').data('gridstack').cellHeight() + $('.grid-stack').data('gridstack').opts.verticalMargin))
                );

                setInterval(function() {
                    chart.load({
                        columns:[
                            ["data1",  Math.floor(Math.random() * 20)],
                            ["data2",  Math.floor(Math.random() * 20)]
                        ]
                    });
                }, 5000);

            });
        </g:javascript>
    </body>
</html>