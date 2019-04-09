package ${package_name}.infra.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import org.dinghuang.core.mybatis.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ${table_annotation}DO
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@TableName("${table_name_full}")
@EqualsAndHashCode(callSuper = true)
public class ${table_name}DO extends BaseModel {

<#if model_column?exists>
    <#list model_column as model>
    /**
     * ${model.columnComment!}
     */
        <#if (model.columnType = 'VARCHAR' || model.columnType = 'TEXT')>
            <#if (model.isPrimary)>
    @TableId
            </#if>
            <#if (model.columnName = 'code' || model.columnName = 'state')>
    @TableField(value = "`${model.columnName}`")
            </#if>
    private String ${model.changeColumnName?uncap_first};
        </#if>
        <#if (model.columnType = 'BIGINT')>
            <#if (model.isPrimary)>
    @TableId(type = IdType.ID_WORKER)
            </#if>
    private Long ${model.changeColumnName?uncap_first};
        </#if>
        <#if model.columnType = 'DATETIME' || model.columnType = 'DATE' >
    private Date ${model.changeColumnName?uncap_first};
        </#if>
        <#if model.columnType = 'TINYINT' >
    @TableField(value = "${model.columnName}")
    private Boolean ${model.changeColumnName?uncap_first};
        </#if>
        <#if model.columnType = 'DOUBLE' >
    @TableField(value = "${model.columnName}")
    private Boolean ${model.changeColumnName?uncap_first};
        </#if>
        <#if model.columnType = 'DECIMAL' >
    private BigDecimal ${model.changeColumnName?uncap_first};
        </#if>

    </#list>
</#if>

}
