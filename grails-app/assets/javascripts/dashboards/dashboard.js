
var serializedData = [];
var displayingCharts = {};

var grid;
var dashboard;

var widgetHtml = '<div class="chart-container"><div class="grid-stack-item-content"></div>';

/**--------------------------------------------
 *              DASHBOARD FUNCTIONS
 *--------------------------------------------/

/**
 * Loads existing widgets into the grid
 * @returns {boolean}
 */
function load(controllerUrl, data, serializedData){
    clear();
    var items = GridStackUI.Utils.sort(serializedData);
    _.each(items, function (node) {
        var chart = addWidget(node, true);
        onChartLoad(controllerUrl, data, chart);
    }, this);
    return false;
}

/**
 * Saves the widget details on the grid
 * @param url - the url to send the data to ('/save')
 * @returns {boolean}
 */
function save(url) {

    collectSerializedData();

    var dashboardGrid = new Grid(serializedData);

    dashboard = new Dashboard($("#dashboard-name").val(), dashboardGrid);

    reloadAfterAjax(url, REST.method.post,  REST.contentType.json, dashboard);

    return false;
}

/**
 * Sends the updated dashboard information to the server
 * @param url - the url to send the updated information to
 */
function update(url, revisionId, revisionNumber){
    collectSerializedData();

    var dashboardGrid = new Grid(serializedData);

    dashboard = new Dashboard($("#dashboard-name").val(), dashboardGrid);
    dashboard.revisionId = revisionId;
    dashboard.revisionNumber = revisionNumber;

    updateContainerHtml(url, REST.method.post,  REST.contentType.json, dashboard, ".overlay-content");

    return false;
}

/**
 * Collects all necessary attributes from DOM elements to store dashboard state
 * The collected data is stored in the 'serializedData' array
 */
function collectSerializedData(){
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
}

/**
 * clears all widgets from the grid
 * @returns {boolean}
 */
function clear() {
    grid.removeAll();
    displayingCharts = {};
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
    var ele = $('<div id="' +  widget.id +'" class="chart-container" data-schemaid="' + widget.schemaId + '" data-chartname="' + widget.chartName + '" data-charttype="' + widget.chartType + '">' +
                    '<div class="chart-crud-bar">' +
                        '<span><i class="widget-img-download fa fa-file-image-o" aria-hidden="true"></i></span>' +
                        '<span><i class="widget-json-download fa fa-file-text-o" aria-hidden="true"></i></span>' +
                        '<span><i class="widget-remove fa fa-times" aria-hidden="true"></i></span>' +
                    '</div>' +
                    '<div class="grid-stack-item-content">' +
                        '<div class="chart"></div>' +
                    '</div>' +
                '</div>');

    //add the element
    var selector = "#" + widget.id + " .chart";

    var chart;

    if(isLoading){
        addLoadedWidget(ele, widget);
        //add the aggregation to the browser cache
        localStorage.setItem(widget.id, JSON.stringify(widget.aggregation));
        chart = getSubscriptionChart(widget.chartType, selector, widget.schemaId);
    }else{
        add(ele);
        chart = getPlaceHolderChart(widget.chartType, selector);
        //add the aggregation to the browser cache
        localStorage.setItem(widget.id, JSON.stringify(JSON.parse(widget.aggregation)));
    }

    displayingCharts[widget.id] = chart;        //add the chart to the displayingCharts object

    resizeWidget("#" + widget.id);

    return chart;
}

/**
 * Removes a widget from the dashboard
 * @param widget - the widget to remove
 */
function removeWidget(widget){
    grid.removeWidget(widget);
    delete displayingCharts[widget.attr('id')];     //remove the property from displayingCharts object
}

/**
 * Resize widget to correct height based on its content
 * @param eleId - the id of the widget
 */
function resizeWidget(eleId){
    var newHeight = Math.ceil(($(eleId + ' .grid-stack-item-content')[0].scrollHeight + grid.opts.verticalMargin) /
                                (grid.cellHeight() + grid.opts.verticalMargin));
    grid.resize($(eleId), $(eleId).attr('data-gs-width'), newHeight);
}

/**
 * Resizes a chart with a new height
 * @param chart - the chart to resize
 * @param newHeight - the new height for the chart
 */
function updateChartHeight(chart, newHeight){
    chart.resize({
        height: newHeight
    });
}

/**
 * Initialises the grid stack grid
 */
function init(){
    $('.grid-stack').gridstack({
        resizable: {
            handles: 'e, se, s, sw, w'
        },
        verticalMargin : '20px'
    });

    grid = $('.grid-stack').data('gridstack');
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

/**
 * Downloads a chart as an image in '.png' format
 * @param widget - the widget containing the chart
 */
function downloadChartImage(widget){
    var chart = displayingCharts[$(widget).attr('id')];
    // Call after the chart finished rendering
    chart.export("image/png", function(dataUrl) {
        const link = document.createElement("a");
        link.setAttribute("href", dataUrl);
        link.setAttribute("download", Date.now() + '.png');
        link.click();
        link.remove();
    });
}

/**
 * Downloads a charts data as json
 * @param widget - the widget element, to obtain the widget id, to access the chart
 */
function downloadChartJson(widget){
    var chart = displayingCharts[$(widget).attr('id')];
    var dataStr = "data:text/json;charset=utf-8," +
        encodeURIComponent(JSON.stringify(chart.data()));

    var downloadAnchorNode = document.createElement('a');
    downloadAnchorNode.setAttribute("href", dataStr);
    downloadAnchorNode.setAttribute("download", "chart.json");
    downloadAnchorNode.click();
    downloadAnchorNode.remove();
}



/**--------------------------------------------
 *              CONSTRUCTORS
 *--------------------------------------------/

/**
 * Dashboard object that is set to server to be saved
 * @param name - name of the dashboard
 * @param grid - the grid with charts
 * @constructor
 */
function Dashboard(name, grid){
    this.name = name;
    this.grid = grid;
}

/**
 * Grid object for storing widget and chart information
 * @param serializedData - the information for widgets and charts
 * @constructor
 */
function Grid(serializedData){
    this.serializedData = serializedData;
}

/**
 * ChartWidget is a widget containing a chart
 * @param uid - unique id of the widget (date in milliseconds)
 * @param chartName - name of the chart
 * @param chartType - type of the chart (BAR, BUBBLE, PIE etc....)
 * @param aggregation - aggregation associated ith the chart
 * @param schemaId - the id of the schema to associate with the chart
 * @constructor
 */
function ChartWidget(uid, chartName, chartType, aggregation, schemaId){
    this.id = uid;
    this.chartName = chartName;
    this.chartType = chartType;
    this.aggregation = aggregation;
    this.schemaId = schemaId;
}
