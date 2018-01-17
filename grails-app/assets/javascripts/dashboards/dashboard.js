
var serializedData = [];

var grid;
var dashboard;

var widgetHtml = '<div class="chart-container"><div class="grid-stack-item-content"></div>';

/**
 * Loads existing widgets into the grid
 * @returns {boolean}
 */
function load(serializedData){
    clear();
    var items = GridStackUI.Utils.sort(serializedData);
    _.each(items, function (node) {
        addWidget(node, true);
    }, this);
    return false;
}

/**
 * Saves the widget details on the grid
 * @param url - the url to go to after the save is successful
 * @returns {boolean}
 */
function save(saveButton) {
    //collect widget information
    serializedData = _.map($('.grid-stack > .grid-stack-item:visible'), function (el) {
        el = $(el);
        var node = el.data('_gridstack_node');
        var widgetInfo = el.data();
        return {
            x: node.x,
            y: node.y,
            width: node.width,
            height: node.height,
            id: node.el[0].id,
            schemaId: widgetInfo.schemaid,
            chartName: widgetInfo.chartname,
            chartType: widgetInfo.charttype,
            aggregation: JSON.parse(localStorage.getItem(node.el[0].id))
        };
    }, this);

    var dashboardGrid = new Grid(serializedData);

    dashboard = new Dashboard($("#dashboard-name").val(), dashboardGrid);

    reloadAfterAjax($(saveButton).attr("href"), REST.method.post,  REST.contentType.json, dashboard);

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
 * Adds a serialized widget to the dashboard
 * @param ele - the container for the widget
 * @param widget - the widget to add
 */
function addLoadedWidget(ele, widget){
    grid.addWidget(ele, widget.x, widget.y, widget.width, widget.height, true);
}

/**
 * Adds a widget to the dashboard
 * @param widget - the widget object to add to the dashboard
 */
function addWidget(widget, isLoading){
    //add widget data attribute to DOM element containing (schemaid, chart-name, chartType)
    var ele = $('<div id="' +  widget.id +'" class="chart-container" data-schemaid="' + widget.schemaId + '" data-chartname="' + widget.name + '" data-charttype="' + widget.chartType + '"><div class="grid-stack-item-content"><div class="chart"></div></div></div>');
    //add the element
    var selector = "#" + widget.id + " .chart";

    var chart;

    if(isLoading){
        addLoadedWidget(ele, widget);
        //add the aggregation to the browser cache
        localStorage.setItem(widget.id, JSON.stringify(widget.aggregation));
        getSubscriptionChart(widget.chartType, selector, widget.schemaId);
    }else{
        add(ele);
        getPlaceHolderChart(widget.chartType, selector);
        //add the aggregation to the browser cache
        localStorage.setItem(widget.id, JSON.stringify(JSON.parse(widget.aggregation)));
    }

    resizeGridAfterAdding("#" + widget.id);

    return chart;
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

function Grid(serializedData){
    this.serializedData = serializedData;
}

function GridCell(chart){
    this.chart = chart;
}

function Chart(name, chartType, aggregation){
    this.name = name;
    this.chartType = chartType;
    this.aggregation = aggregation;
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
    return new ChartWidget("widget-" + new Date().getTime(),
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