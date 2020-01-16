package ${package_name};

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

/**
 * ${table_annotation}UpdateDTO
 *
 * @author ${author}
 * @since ${date}
 */
@Data
@ApiModel(description = "${table_annotation}")
public class ${table_name}UpdateDTO {

<#if model_column?exists>
    <#list model_column as model>
        <#if (model.columnType = 'VARCHAR' || model.columnType = 'TEXT' || model.columnType = 'VARCHAR2' || model.columnType = 'CHARACTER')>
    @ApiModelProperty(notes = "${model.columnComment!}")
    @Length(max = ${model.columnLength!}, min = 0, message = "${table_annotation}的${model.columnComment!}长度不能超过${model.columnLength!}")
            <#if (model.isNullAble)>
    @NotBlank(message = "${table_annotation}的${model.columnComment!}不能为空")
            </#if>
    private String ${model.changeColumnName?uncap_first};
        </#if>
        <#if (model.columnType = 'BIGINT' || model.columnType = 'NUMBER' )>
    @ApiModelProperty(notes = "${model.columnComment!}")
            <#if (model.isNullAble)>
    @NotNull(message = "${table_annotation}的${model.columnComment!}不能为空")
            </#if>
    private Long ${model.changeColumnName?uncap_first};
        </#if>
        <#if model.columnType = 'DATETIME' || model.columnType = 'DATE' >
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
    @ApiModelProperty(notes = "乐观锁版本号")
    @NotNull(message = "乐观锁不能为空")
    private Long objectVersionNumber;

</#if>

}
