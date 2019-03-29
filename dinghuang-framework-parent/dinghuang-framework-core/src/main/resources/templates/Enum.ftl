package ${package_name}.infra.dataobject.enums;

/**
 * ${table_annotation}Enum
 *
 * @author ${author}
 * @since ${date}
 */
public enum ${table_name}Enum {

<#if model_column?exists>
    <#list model_column as model>
    /**
     * ${model.columnComment!}
     */
        <#if model_has_next>
            <#if (model.columnName = 'code' || model.columnName = 'state')>
    ${model.changeColumnNameUp}("`${model.columnName}`"),
            </#if>
            <#if (model.columnName != 'code' && model.columnName != 'state')>
    ${model.changeColumnNameUp}("${model.columnName}"),
            </#if>

        </#if>
        <#if !model_has_next>
    ${model.changeColumnNameUp}("${model.columnName}");

        </#if>
    </#list>
</#if>
    private String value;

    ${table_name}Enum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
