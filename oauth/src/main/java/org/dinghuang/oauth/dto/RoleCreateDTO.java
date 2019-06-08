package org.dinghuang.oauth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 角色CreateDTO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
@Data
@ApiModel(description = "角色")
public class RoleCreateDTO {


    @ApiModelProperty(notes = "用户id")
    private Long userId;

    @ApiModelProperty(notes = "角色名称")
    @Length(max = 200, min = 0, message = "name:角色的角色名称长度不能超过200")
    private String name;


}
