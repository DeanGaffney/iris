<g:render template="fields/fields"/>

<g:if test="${hiddenValue != 'value_count'}">
<div id="metric-missing" class="agg-option-container col-6">
    <label class="col-2 col-form-label">Missing</label>
    <div class="col-6">
        <input id="metric-missing-input" class="form-control" type="number" value="" >
    </div>
</div>
</g:if>

<div class="col-6">
    <input id="hidden-input" class="form-control" type="hidden" value="${hiddenValue}" >
</div>
