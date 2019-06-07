package org.dinghuang.demo.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 订单UpdateDTO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Data
@ApiModel(description = "订单")
public class OrderUpdateDTO {

    @ApiModelProperty(notes = "主键ID")
    @NotNull(message = "id:主键ID不能为空")
    private Long id;

    @ApiModelProperty(notes = "名称")
    @Length(max = 100, min = 0, message = "name:订单的名称长度不能超过100")
    private String name;

    @ApiModelProperty(notes = "订单名称")
    @Length(max = 20, min = 0, message = "orderName:订单的订单名称长度不能超过20")
    private String orderName;

    @ApiModelProperty(notes = "乐观锁版本号")
    @NotNull(message = "objectVersionNumber:乐观锁不能为空")
    private Long objectVersionNumber;


}
