package org.dinghuang.oauth.mapper;

import org.dinghuang.oauth.infra.dataobject.UserDO;
import org.dinghuang.oauth.dto.UserDTO;
import org.dinghuang.oauth.dto.UserCreateDTO;
import org.dinghuang.oauth.dto.UserUpdateDTO;
import org.dinghuang.oauth.dto.UserCreateOrUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.mybatis.model.Pageable;

import java.util.List;

/**
 * 用户信息Mapper
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * createDto到do的转换
     *
     * @param userCreateDTO userCreateDTO
     * @return UserDO
     */
    UserDO createDtoToDo(UserCreateDTO userCreateDTO);

    /**
     * updateDto到do的转换
     *
     * @param userUpdateDTO userUpdateDTO
     * @return UserDO
     */
    UserDO updateDtoToDo(UserUpdateDTO userUpdateDTO);

    /**
     * createOrUpdateDto到dto的转换
     *
     * @param userCreateOrUpdateDTO userCreateOrUpdateDTO
     * @return UserDO
     */
    UserDO createOrUpdateDtoToDo(UserCreateOrUpdateDTO userCreateOrUpdateDTO);

    /**
     * do到dto的转换
     *
     * @param userDO userDO
     * @return UserDTO
     */
    UserDTO doToDto(UserDO userDO);

    /**
     * do到dto列表转换
     *
     * @param userDOS userDOS
     * @return UserDTO
     */
    List<UserDTO> doToDtos(List<UserDO> userDOS);

    /**
     * do到dto分页转换
     *
     * @param userDOPageable userDOPageable
     * @return UserDTO
     */
    PageableDTO<UserDTO> pageDoToDto(Pageable<UserDO> userDOPageable);
}