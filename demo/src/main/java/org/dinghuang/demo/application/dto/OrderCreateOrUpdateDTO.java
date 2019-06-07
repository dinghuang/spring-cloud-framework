package org.dinghuang.demo.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 订单UpdateDTO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Data
@ApiModel(description = "订单")
public class OrderCreateOrUpdateDTO {

    @ApiModelProperty(notes = "主键ID")
    private Long id;

    @ApiModelProperty(notes = "名称")
    @Length(max = 100, min = 0, message = "name:订单的名称长度不能超过100")
    private String name;

    @ApiModelProperty(notes = "订单名称")
    @Length(max = 20, min = 0, message = "orderName:订单的订单名称长度不能超过20")
    private String orderName;

    @ApiModelProperty(notes = "乐观锁版本号")
    private Long objectVersionNumber;


}
