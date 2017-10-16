<div id="schema-field-form-${index}" class="schemaField-form">
    <div>
        <h3>Schema Field ${index}</h3>
        <g:textField name="schema-field-name-${index}" value="schemaFields[${index}].name" />
        <g:select name="schema-field-type-${index}" from="${fieldTypes}"/>
    </div>
</div>
