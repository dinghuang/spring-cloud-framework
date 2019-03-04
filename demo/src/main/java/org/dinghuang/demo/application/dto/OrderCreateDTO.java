package org.dinghuang.demo.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
@Data
@ApiModel(description = "创建订单详情")
public class OrderCreateDTO {
    @ApiModelProperty(notes = "客户名称")
    private String customerName;
    @ApiModelProperty(notes = "描述")
    private String description;
}
