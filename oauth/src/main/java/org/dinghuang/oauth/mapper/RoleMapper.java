package org.dinghuang.oauth.mapper;

import org.dinghuang.oauth.infra.dataobject.RoleDO;
import org.dinghuang.oauth.dto.RoleDTO;
import org.dinghuang.oauth.dto.RoleCreateDTO;
import org.dinghuang.oauth.dto.RoleUpdateDTO;
import org.dinghuang.oauth.dto.RoleCreateOrUpdateDTO;
import org.dinghuang.oauth.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.mybatis.model.Pageable;

import java.util.List;

/**
 * 角色Mapper
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    /**
     * createDto到do的转换
     *
     * @param roleCreateDTO roleCreateDTO
     * @return RoleDO
     */
    RoleDO createDtoToDo(RoleCreateDTO roleCreateDTO);

    /**
     * updateDto到do的转换
     *
     * @param roleUpdateDTO roleUpdateDTO
     * @return RoleDO
     */
    RoleDO updateDtoToDo(RoleUpdateDTO roleUpdateDTO);

    /**
     * createOrUpdateDto到dto的转换
     *
     * @param roleCreateOrUpdateDTO roleCreateOrUpdateDTO
     * @return RoleDO
     */
    RoleDO createOrUpdateDtoToDo(RoleCreateOrUpdateDTO roleCreateOrUpdateDTO);

    /**
     * do到dto的转换
     *
     * @param roleDO roleDO
     * @return RoleDTO
     */
    RoleDTO doToDto(RoleDO roleDO);

    /**
     * do到dto列表转换
     *
     * @param roleDOS roleDOS
     * @return RoleDTO
     */
    List<RoleDTO> doToDtos(List<RoleDO> roleDOS);

    /**
     * do到dto分页转换
     *
     * @param roleDOPageable roleDOPageable
     * @return RoleDTO
     */
    PageableDTO<RoleDTO> pageDoToDto(Pageable<RoleDO> roleDOPageable);

    List<Role> doToRoles(List<RoleDO> roleDOS);

    Role doToRole(RoleDO roleDO);
}