package org.dinghuang.demo.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
@Data
@ApiModel(description = "更新订单详情")
public class OrderUpdateDTO {
    @ApiModelProperty(notes = "主键id")
    private Long uuid;
    @ApiModelProperty(notes = "客户名称")
    private String customerName;
    @ApiModelProperty(notes = "描述")
    private String description;
    @ApiModelProperty(notes = "乐观锁")
    private Integer version;
}
