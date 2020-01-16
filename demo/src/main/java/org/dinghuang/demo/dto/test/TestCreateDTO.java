package org.dinghuang.demo.dto.test;

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
 * 测试对象CreateDTO
 *
 * @author dinghuang123@gmail.com
 * @since 2020/01/16
 */
@Data
@ApiModel(description = "测试对象")
public class TestCreateDTO {


    @ApiModelProperty(notes = "姓名")
    @Length(min = 0, message = "测试对象的姓名长度不能超过")
    @NotBlank(message = "测试对象的姓名不能为空")
    private String name;

    @ApiModelProperty(notes = "性别")
    @Length(min = 0, message = "测试对象的性别长度不能超过")
    @NotBlank(message = "测试对象的性别不能为空")
    private String sex;


}
