package org.dinghuang.demo.dto.test;

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
 * 测试对象UpdateDTO
 *
 * @author dinghuang123@gmail.com
 * @since 2020/01/16
 */
@Data
@ApiModel(description = "测试对象")
public class TestUpdateDTO {

    @ApiModelProperty(notes = "主键")
    @NotNull(message = "测试对象的主键不能为空")
    private Long id;

    @ApiModelProperty(notes = "姓名")
    @Length(min = 0, message = "测试对象的姓名长度不能超过")
    @NotBlank(message = "测试对象的姓名不能为空")
    private String name;

    @ApiModelProperty(notes = "性别")
    @Length(min = 0, message = "测试对象的性别长度不能超过")
    @NotBlank(message = "测试对象的性别不能为空")
    private String sex;

    @ApiModelProperty(notes = "乐观锁版本号")
    @NotNull(message = "乐观锁不能为空")
    private Long objectVersionNumber;


}
