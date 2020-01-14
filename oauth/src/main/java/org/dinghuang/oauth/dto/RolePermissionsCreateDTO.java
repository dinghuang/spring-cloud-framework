package org.dinghuang.oauth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 角色权限CreateDTO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
@Data
@ApiModel(description = "角色权限")
public class RolePermissionsCreateDTO {


    @ApiModelProperty(notes = "路径")
    @Length(max = 200, min = 0, message = "url:角色权限的路径长度不能超过200")
    private String url;

    @ApiModelProperty(notes = "角色名称")
    @Length(max = 200, min = 0, message = "roleName:角色权限的角色名称长度不能超过200")
    private String roleName;

    @ApiModelProperty(notes = "角色id")
    private Long roleId;

    @ApiModelProperty(notes = "用户id")
    private Long userId;


}
