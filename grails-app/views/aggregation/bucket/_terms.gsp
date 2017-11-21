
<g:render template="fields/fields"/>

<div id="terms-size agg-form" class="agg-option-container col-6">
    <label class="col-2 col-form-label">Size</label>
    <div class="col-6">
        <input id="terms-size-input" class="form-control" type="number" value="" >
    </div>
</div>

<div id="terms-shard-size" class="agg-option-container col-6">
    <label class="col-2 col-form-label">Shard size</label>
    <div class="col-6">
        <input id="terms-shard-size-input" class="form-control" type="number" value="" >
    </div>
</div>

<div id="terms-min-doc-count" class="agg-option-container col-6">
    <label class="col-2 col-form-label">Min Doc Count</label>
    <div class="col-6">
        <input id="terms-min-doc-count-input" class="form-control" type="number" value="" >
    </div>
</div>

<g:render template="operation/order"/>

<div class="col-6">
    <input id="hidden-input" class="form-control" type="hidden" value="${hiddenValue}" >
</div>
