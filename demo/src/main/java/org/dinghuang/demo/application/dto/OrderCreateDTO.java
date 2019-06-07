package org.dinghuang.demo.application.dto;

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
 * 订单CreateDTO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Data
@ApiModel(description = "订单")
public class OrderCreateDTO {


    @ApiModelProperty(notes = "名称")
    @Length(max = 100, min = 0, message = "name:订单的名称长度不能超过100")
    private String name;

    @ApiModelProperty(notes = "订单名称")
    @Length(max = 20, min = 0, message = "orderName:订单的订单名称长度不能超过20")
    private String orderName;


}
