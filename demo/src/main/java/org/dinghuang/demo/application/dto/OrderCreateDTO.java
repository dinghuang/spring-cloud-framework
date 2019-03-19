package org.dinghuang.demo.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
@Data
@ApiModel(description = "创建订单详情")
public class OrderCreateDTO {

    @NotNull(message = "客户名称不能为空")
    @Length(max = 10, message = "客户名称请控制在10个字符以内")
    @ApiModelProperty(notes = "客户名称")
    private String customerName;

    @ApiModelProperty(notes = "描述")
    @Length(max = 5000, message = "描述不能超过5000")
    private String description;
}
