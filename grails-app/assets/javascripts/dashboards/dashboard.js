
// var serializedData = [
//     {x: 0, y: 0, width: 2, height: 2},
//     {x: 3, y: 1, width: 1, height: 2},
//     {x: 4, y: 1, width: 1, height: 1},
//     {x: 2, y: 3, width: 3, height: 1},
//     {x: 1, y: 4, width: 1, height: 1},
//     {x: 1, y: 3, width: 1, height: 1},
//     {x: 2, y: 4, width: 1, height: 1},
//     {x: 2, y: 5, width: 1, height: 1}
// ];

var serializedData = [];

var grid;

var widgetHtml = '<div class="chart-container"><div class="grid-stack-item-content"></div>';

//load the dashboard
function load(){
    clear();
    var items = GridStackUI.Utils.sort(serializedData);
    _.each(items, function (node) {
        addNode(node);
    }, this);
    return false;
}

//save the widget details of the dashboard
function save() {
    serializedData = _.map($('.grid-stack > .grid-stack-item:visible'), function (el) {
        el = $(el);
        var node = el.data('_gridstack_node');
        return {
            x: node.x,
            y: node.y,
            width: node.width,
            height: node.height
        };
    }, this);
    console.log(JSON.stringify(serializedData, null, 4));
    return false;
}

//clear the dashboard
function clear() {
    grid.removeAll();
    return false;
}

//add a saved node to widget
function addNode(node){
    var elem = $(this.widgetHtml);
    grid.addWidget(elem, node.x, node.y, node.width, node.height, true);
}

//add new widget to the dashboard
function add(){
    var elem = $(this.widgetHtml);
    grid.addWidget(elem, 0, 0 , 3, 3, true);
}

function add(ele){
    grid.addWidget(ele, 0, 0, 3, 3, true);
}

//init dashboard, gets called at construction
function init(){
    $('.grid-stack').gridstack({
        resizable: {
            handles: 'e, se, s, sw, w'
        }
    });

    grid = $('.grid-stack').data('gridstack');
}

function Dashboard(){

}

function WidgetChart(uid, name, chartType, aggregation, schemaId){
    this.id = uid;
    this.name = name;
    this.chartType = chartType;
    this.aggregation = aggregation;
    this.schemaId = schemaId;
}

function getAddedWidgetInfo(){
    return new WidgetChart("widget-" + new Date().getTime(),
                           $("#chart-name").val(),
                           $("#chart-type").val(),
                           $("#aggregation-text-area").val(),
                           $("#schema-select").val());
}

function clearWidgetModal(){
    $("#chart-name").val($("#char-name").attr("placeholder"));          //reset chart name
    $("#chart-type").val("Bar");                                        //reset chart type
    $("#aggregation-text-area").val("");                                //reset agg text area
    $("#schema-select").val("null");                                    //reset schema select
}

function showWidgetModal(){
    $("#widget-modal").modal({
        show: true,
        backdrop: true
    });
}

function hideWidgetModal(){
    $("#widget-modal").modal("toggle");
}

function addWidget(widget){
    //'<div class="chart-container"><div class="grid-stack-item-content"></div>';
    var ele = $('<div id="' +  widget.id +'" class="chart-container"><div class="grid-stack-item-content"><div class="chart"></div></div></div>');
    //add the element
    add(ele);

    var selector = "#" + widget.id + " .chart";
    console.log(selector);

    var chart = getPlaceHolderChart(widget.chartType, selector);

    gridResizeAfterAdd();
}

function gridResizeAfterAdd(){
    grid.resize(
             $('.grid-stack-item')[0],
             $($('.grid-stack-item')[0]).attr('data-gs-width'),
             Math.ceil(($('.grid-stack-item-content')[0].scrollHeight + $('.grid-stack').data('gridstack').opts.verticalMargin) / ($('.grid-stack').data('gridstack').cellHeight() + $('.grid-stack').data('gridstack').opts.verticalMargin))
    );
}


