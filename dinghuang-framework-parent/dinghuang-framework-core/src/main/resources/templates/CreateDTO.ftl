package ${package_name};

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

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
        <#if (!model.isPrimary && (model.columnType = 'VARCHAR' || model.columnType = 'TEXT' || model.columnType = 'VARCHAR2' || model.columnType = 'CHARACTER' || model.columnType = 'CHARACTER VARYING'))>
    @ApiModelProperty(notes = "${model.columnComment!}")
    @Length(<#if (model.columnLength)??>max = ${model.columnLength!},</#if>min = 0, message = "${table_annotation}的${model.columnComment!}长度不能超过${model.columnLength!}")
            <#if (model.isNullAble)>
    @NotBlank(message = "${table_annotation}的${model.columnComment!}不能为空")
            </#if>
    private String ${model.changeColumnName?uncap_first};
        </#if>
        <#if (!model.isPrimary && (model.columnType = 'BIGINT' || model.columnType = 'NUMBER'))>
    @ApiModelProperty(notes = "${model.columnComment!}")
            <#if (model.isNullAble)>
    @NotNull(message = "${table_annotation}的${model.columnComment!}不能为空")
            </#if>
    private Long ${model.changeColumnName?uncap_first};
        </#if>
        <#if (!model.isPrimary && (model.columnType = 'DATETIME' || model.columnType = 'DATE'))>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "${model.columnComment!}")
            <#if (model.isNullAble)>
    @NotNull(message = "${table_annotation}的${model.columnComment!}不能为空")
            </#if>
    private Date ${model.changeColumnName?uncap_first};
        </#if>
        <#if (!model.isPrimary && model.columnType = 'TINYINT')>
    @ApiModelProperty(notes = "${model.columnComment!}")
            <#if (model.isNullAble)>
    @NotNull(message = "${table_annotation}的${model.columnComment!}不能为空")
            </#if>
    private Boolean ${model.changeColumnName?uncap_first};
        </#if>
        <#if model.columnType = 'DOUBLE'>
    @ApiModelProperty(notes = "${model.columnComment!}")
            <#if (model.isNullAble)>
    @NotNull(message = "${table_annotation}的${model.columnComment!}不能为空")
            </#if>
    private Double ${model.changeColumnName?uncap_first};
        </#if>
        <#if model.columnType = 'DECIMAL'>
    @ApiModelProperty(notes = "${model.columnComment!}")
            <#if (model.isNullAble)>
    @NotNull(message = "${table_annotation}的${model.columnComment!}不能为空")
            </#if>
    private BigDecimal ${model.changeColumnName?uncap_first};
        </#if>

    </#list>
</#if>

}
