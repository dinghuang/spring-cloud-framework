package org.dinghuang.oauth.mapper;

import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.mybatis.model.Pageable;
import org.dinghuang.oauth.dto.RolePermissionsCreateDTO;
import org.dinghuang.oauth.dto.RolePermissionsCreateOrUpdateDTO;
import org.dinghuang.oauth.dto.RolePermissionsDTO;
import org.dinghuang.oauth.dto.RolePermissionsUpdateDTO;
import org.dinghuang.oauth.infra.dataobject.RolePermissionsDO;
import org.dinghuang.oauth.model.RolePermissions;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 角色权限Mapper
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
@Mapper
public interface RolePermissionsMapper {

    RolePermissionsMapper INSTANCE = Mappers.getMapper(RolePermissionsMapper.class);

    /**
     * createDto到do的转换
     *
     * @param rolePermissionsCreateDTO rolePermissionsCreateDTO
     * @return RolePermissionsDO
     */
    RolePermissionsDO createDtoToDo(RolePermissionsCreateDTO rolePermissionsCreateDTO);

    /**
     * updateDto到do的转换
     *
     * @param rolePermissionsUpdateDTO rolePermissionsUpdateDTO
     * @return RolePermissionsDO
     */
    RolePermissionsDO updateDtoToDo(RolePermissionsUpdateDTO rolePermissionsUpdateDTO);

    /**
     * createOrUpdateDto到dto的转换
     *
     * @param rolePermissionsCreateOrUpdateDTO rolePermissionsCreateOrUpdateDTO
     * @return RolePermissionsDO
     */
    RolePermissionsDO createOrUpdateDtoToDo(RolePermissionsCreateOrUpdateDTO rolePermissionsCreateOrUpdateDTO);

    /**
     * do到dto的转换
     *
     * @param rolePermissionsDO rolePermissionsDO
     * @return RolePermissionsDTO
     */
    RolePermissionsDTO doToDto(RolePermissionsDO rolePermissionsDO);

    /**
     * do到dto列表转换
     *
     * @param rolePermissionsDOS rolePermissionsDOS
     * @return RolePermissionsDTO
     */
    List<RolePermissionsDTO> doToDtos(List<RolePermissionsDO> rolePermissionsDOS);

    /**
     * do到dto分页转换
     *
     * @param rolePermissionsDOPageable rolePermissionsDOPageable
     * @return RolePermissionsDTO
     */
    PageableDTO<RolePermissionsDTO> pageDoToDto(Pageable<RolePermissionsDO> rolePermissionsDOPageable);

    List<RolePermissions> doToRolePermissions(List<RolePermissionsDO> rolePermissionsDOS);

    RolePermissions doToRolePermissions(RolePermissionsDO rolePermissionsDO);
}