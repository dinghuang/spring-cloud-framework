package org.dinghuang.oauth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 用户信息CreateDTO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Data
@ApiModel(description = "用户信息")
public class UserCreateDTO {

    @ApiModelProperty(notes = "用户名")
    @Length(max = 200, min = 0, message = "account:用户信息的用户名长度不能超过200")
    @NotBlank(message = "用户名不能为空")
    private String account;

    @ApiModelProperty(notes = "密码")
    @Length(max = 50, min = 0, message = "password:用户信息的密码长度不能超过50")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(notes = "用户姓名")
    @Length(max = 100, min = 0, message = "name:用户信息的用户姓名长度不能超过100")
    @NotBlank(message = "用户姓名不能为空")
    private String name;

    @ApiModelProperty(notes = "手机号码")
    @Length(max = 200, min = 0, message = "mobilePhone:用户信息的手机号码长度不能超过200")
    @NotBlank(message = "手机号码不能为空")
    private String mobilePhone;

    @ApiModelProperty(notes = "号码")
    @Length(max = 200, min = 0, message = "phone:用户信息的号码长度不能超过200")
    private String phone;

    @ApiModelProperty(notes = "邮箱")
    @Length(max = 50, min = 0, message = "email:用户信息的邮箱长度不能超过50")
    private String email;

    @ApiModelProperty(notes = "组织编码")
    @Length(max = 50, min = 0, message = "orgCode:用户信息的组织编码长度不能超过50")
    private String orgCode;


}
