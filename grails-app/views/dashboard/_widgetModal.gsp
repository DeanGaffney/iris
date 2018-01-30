<div id="widget-modal" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title">Add Widget</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <div class="modal-body">

                <!--ACCORDION-->
                <div id="accordion" role="tablist" aria-multiselectable="true">

                    <!--CHART CREATION-->
                    <div class="card">

                        <div class="card-header" role="tab" id="chart-creation-header">
                            <h5 class="mb-0">
                                <a data-toggle="collapse" data-parent="#accordion" href="#chart-creation" aria-expanded="false" aria-controls="chart-creation">
                                    Chart
                                </a>
                            </h5>
                        </div>

                        <div id="chart-creation" class="collapse show" role="tabpanel" aria-labelledby="chart-heading">
                            <div class="card-block">
                                <div class="form-group">

                                    <label for="chart-name">Chart Name</label>
                                    <input type="text" class="form-control" id="chart-name" placeholder="Name...">

                                    <label for="chart-type">Example multiple select</label>

                                    <g:select v-model="chartType" @change="updateWidgetArea()" name="chart-type" from="${com.wit.iris.charts.enums.ChartType.values()*.getValue()}"
                                              keys="${com.wit.iris.charts.enums.ChartType.values()*.getValue()}"
                                              class="form-control custom-select"
                                              href="${createLink(controller: 'dashboard', action: 'getWidgetChartTemplate')}"/>


                                </div>
                            </div>
                        </div>
                    </div>
                    <!--CHART CREATION END-->

                    <!--AGGREGATION CREATION-->
                    <template v-if="needsAggregation">
                        <div class="card">

                            <div class="card-header" role="tab" id="aggregation-header">
                                <h5 class="mb-0">
                                    <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#aggregation-creation" aria-expanded="false" aria-controls="aggregation-creation">
                                        Aggregation
                                    </a>
                                </h5>
                            </div>

                            <div id="aggregation-creation" class="collapse" role="tabpanel" aria-labelledby="aggregation-header">
                                <div class="card-block">
                                    <div id="aggregation-area">

                                    </div>
                                </div>
                            </div>

                        </div>
                        <!--AGGREGATION CREATION END-->
                    </template>

                    <!--STATE DISC CREATION START-->

                    <template v-else-if="isStateDiscChart">
                        <div class="card">

                            <div class="card-header" role="tab" id="state-disc-header">
                                <h5 class="mb-0">
                                    <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#state-disc-creation" aria-expanded="false" aria-controls="state-disc-creation">
                                        State Disc
                                    </a>
                                </h5>
                            </div>

                            <div id="state-disc-creation" class="collapse" role="tabpanel" aria-labelledby="state-disc-header">
                                <div class="card-block">
                                    <div id="state-disc-area">

                                    </div>
                                </div>
                            </div>

                        </div>
                        <!--STATE DISC CREATION END-->
                    </template>

                    <!--STATE LIST CREATION START-->
                    <template v-else-if="isStateListChart">
                        <div class="card">

                            <div class="card-header" role="tab" id="state-list-header">
                                <h5 class="mb-0">
                                    <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#state-list-creation" aria-expanded="false" aria-controls="state-list-creation">
                                        State List
                                    </a>
                                </h5>
                            </div>

                            <div id="state-list-creation" class="collapse" role="tabpanel" aria-labelledby="state-list-header">
                                <div class="card-block">
                                    <div id="state-list-area">

                                    </div>
                                </div>
                            </div>

                        </div>
                        <!--STATE LIST CREATION END-->
                    </template>


                </div>
            </div>

            <div class="modal-footer">
                <button type="button" id="add-widget-modal-btn" class="btn ml-1">Add</button>
            </div>

        </div>
    </div>
</div>

<g:javascript>

    var widgetApp = new Vue({
        el: "#widget-modal",
        data: {
            chartType: 'Bar'
        },
        computed: {
            isStateDiscChart: function () {
                return this.chartType == 'StateDisc';
            },
            isStateListChart: function () {
                return this.chartType == "StateList";
            },
            needsAggregation: function () {
                return this.chartType != "StateList" && this.chartType != "StateDisc";
            }
        },
        methods: {
            updateWidgetArea: function () {
                var url = $("#chart-type").attr("href");
                updateContainerHtml(url, REST.method.post, REST.contentType.json, {chartType: this.chartType}, getWidgetAreaToUpdate());
            }
        }
    });

    widgetApp.updateWidgetArea();

    function getWidgetAreaToUpdate() {
        return (widgetApp.chartType == 'StateDisc') ? "#state-disc-area" : (widgetApp.chartType == 'StateList') ?
                "#state-list-area" : "#aggregation-area";
    }

</g:javascript>