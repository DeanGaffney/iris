
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

//init dashboard, gets called at construction
function init(){
    $('.grid-stack').gridstack({
        resizable: {
            handles: 'e, se, s, sw, w'
        }
    });

    grid = $('.grid-stack').data('gridstack');
}

