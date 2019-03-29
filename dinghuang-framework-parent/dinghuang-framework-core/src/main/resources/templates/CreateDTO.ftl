package ${package_name}.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ${table_annotation}CreateDTO
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@ApiModel(description = "${table_annotation}")
public class ${table_name}CreateDTO {

<#if model_column?exists>
    <#list model_column as model>
        <#if (!model.isPrimary && (model.columnType = 'VARCHAR' || model.columnType = 'TEXT'))>
    @ApiModelProperty(notes = "${model.columnComment!}")
    @Length(max = ${model.columnLength!}, min = 0, message = "${model.changeColumnName?uncap_first}:${table_annotation}的${model.columnComment!}长度不能超过${model.columnLength!}")
            <#if (model.isNullAble)>
    @NotBlank(message = "${model.changeColumnName?uncap_first}:${model.columnComment!}不能为空")
            </#if>
    private String ${model.changeColumnName?uncap_first};
        </#if>
        <#if (!model.isPrimary && model.columnType = 'BIGINT')>
    @ApiModelProperty(notes = "${model.columnComment!}")
            <#if (model.isNullAble)>
    @NotNull(message = "${model.changeColumnName?uncap_first}:${model.columnComment!}不能为空")
            </#if>
    private Long ${model.changeColumnName?uncap_first};
        </#if>
        <#if (!model.isPrimary && (model.columnType = 'DATETIME' || model.columnType = 'DATE'))>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "${model.columnComment!}")
            <#if (model.isNullAble)>
    @NotNull(message = "${model.changeColumnName?uncap_first}:${model.columnComment!}不能为空")
            </#if>
    private Date ${model.changeColumnName?uncap_first};
        </#if>
        <#if (!model.isPrimary && model.columnType = 'TINYINT')>
    @ApiModelProperty(notes = "${model.columnComment!}")
            <#if (model.isNullAble)>
    @NotNull(message = "${model.changeColumnName?uncap_first}:${model.columnComment!}不能为空")
            </#if>
    private Boolean ${model.changeColumnName?uncap_first};
        </#if>
        <#if model.columnType = 'DECIMAL'>
    @ApiModelProperty(notes = "${model.columnComment!}")
            <#if (model.isNullAble)>
    @NotNull(message = "${model.changeColumnName?uncap_first}:${model.columnComment!}不能为空")
            </#if>
    private BigDecimal ${model.changeColumnName?uncap_first};
        </#if>

    </#list>
</#if>

}
