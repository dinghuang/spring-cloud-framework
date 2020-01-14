package org.dinghuang.oauth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 角色UpdateDTO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
@Data
@ApiModel(description = "角色")
public class RoleCreateOrUpdateDTO {

    @ApiModelProperty(notes = "主键ID")
    private Long id;

    @ApiModelProperty(notes = "用户id")
    private Long userId;

    @ApiModelProperty(notes = "角色名称")
    @Length(max = 200, min = 0, message = "name:角色的角色名称长度不能超过200")
    private String name;

    @ApiModelProperty(notes = "乐观锁版本号")
    private Long objectVersionNumber;


}
