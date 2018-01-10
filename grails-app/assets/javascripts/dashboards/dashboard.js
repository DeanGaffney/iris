
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

/**
 * Loads existing widgets into the grid
 * @returns {boolean}
 */
function load(){
    clear();
    var items = GridStackUI.Utils.sort(serializedData);
    _.each(items, function (node) {
        addNode(node);      //needs to be changed to have a method that ids id to the dom element
    }, this);
    return false;
}

/**
 * Saves the widget details on the grid
 * @returns {boolean}
 */
function save() {
    //collect widget information
    serializedData = _.map($('.grid-stack > .grid-stack-item:visible'), function (el) {
        el = $(el);
        var node = el.data('_gridstack_node');
        return {
            x: node.x,
            y: node.y,
            width: node.width,
            height: node.height,
            id: node.el[0].id
        };
    }, this);
    _.each(serializedData, function(node){
        console.log(node);
        // function GridCell(chart, gridPosition){
        //     this.uid = uid;
        //     this.chart = chart;
        //     this.gridPosition = gridPosition;
        // }
        // var gridCell = new GridCell(node.id, );
        //USE HTML 'data' attribute to store schema id, aggegation, chart name, chart id etc.....
    });
    return false;
}

/**
 * clears all widgets from the grid
 * @returns {boolean}
 */
function clear() {
    grid.removeAll();
    return false;
}

/**
 * Add an existing saved widget to the grid
 * @param node
 */
function addNode(node){
    var elem = $(this.widgetHtml);
    grid.addWidget(elem, node.x, node.y, node.width, node.height, true);
}

/**
 * Adds an element to the grid
 */
function add(){
    var elem = $(this.widgetHtml);
    grid.addWidget(elem, 0, 0 , 3, 3, true);
}

/**
 * Adds the element to the grid stack grid
 * @param ele
 */
function add(ele){
    return grid.addWidget(ele, 0, 0, 3, 3, true);
}

/**
 * Initialises the grid stack grid
 */
function init(){
    $('.grid-stack').gridstack({
        resizable: {
            handles: 'e, se, s, sw, w'
        }
    });

    grid = $('.grid-stack').data('gridstack');
}

function Dashboard(name, grid){
    this.name = name;
    this.grid = grid;
}

function Grid(gridCellPositions){
    this.gridCellPositions = gridCellPositions;
    this.gridCells = [];
}

function GridCell(chart, gridPosition){
    this.uid = uid;
    this.chart = chart;
    this.gridPosition = gridPosition;
}

/**
 * ChartWidget is a widget containing a chart
 * @param uid - unique id of the widget (date in milliseconds)
 * @param name - name of the chart
 * @param chartType - type of the chart (BAR, BUBBLE, PIE etc....)
 * @param aggregation - aggregation associated ith the chart
 * @param schemaId - the id of the schema to associate with the chart
 * @constructor
 */
function ChartWidget(uid, name, chartType, aggregation, schemaId){
    this.id = uid;
    this.name = name;
    this.chartType = chartType;
    this.aggregation = aggregation;
    this.schemaId = schemaId;
}

/**
 * Creates and returns a Chart Widget object
 * which was created from the widget creation modal
 * @returns {ChartWidget}
 */
function getAddedWidgetInfo(){
    return new ChartWidget("widget-" + new Date().getTime() + "-" + $("#schema-select").val(),
                           $("#chart-name").val(),
                           $("#chart-type").val(),
                           $("#aggregation-text-area").val(),
                           $("#schema-select").val());
}

/**
 * Clears the content in the widget creation modal
 * back to their default values
 */
function clearWidgetModal(){
    $("#chart-name").val($("#char-name").attr("placeholder"));          //reset chart name
    $("#chart-type").val("Bar");                                        //reset chart type
    $("#aggregation-text-area").val("");                                //reset agg text area
    $("#schema-select").val("null");                                    //reset schema select
}

/**
 * Shows the widget creation modal
 */
function showWidgetModal(){
    $("#widget-modal").modal({
        show: true,
        backdrop: true
    });
}

/**
 * Hides the widget creation modal
 */
function hideWidgetModal(){
    $("#widget-modal").modal("toggle");
}

/**
 * Adds a widget to the dashboard
 * @param widget - the widget object to add to the dashboard
 */
function addWidget(widget){
    var ele = $('<div id="' +  widget.id +'" class="chart-container"><div class="grid-stack-item-content"><div class="chart"></div></div></div>');
    //add the element
    add(ele);

    var selector = "#" + widget.id + " .chart";

    var chart = getPlaceHolderChart(widget.chartType, selector);

    resizeGridAfterAdding("#" + widget.id);
}

/**
 * Resize widget to correct height based on its content
 * @param eleId - the id of the widget
 */
function resizeGridAfterAdding(eleId){
    grid.resize(
        $(eleId),
        $(eleId).attr('data-gs-width'),
        Math.ceil(($(eleId + ' .grid-stack-item-content')[0].scrollHeight + grid.opts.verticalMargin) / (grid.cellHeight() + grid.opts.verticalMargin))
    );
}


