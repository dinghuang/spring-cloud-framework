package org.dinghuang.oauth.mapper;

import org.dinghuang.oauth.infra.dataobject.OauthClientDO;
import org.dinghuang.oauth.dto.OauthClientDTO;
import org.dinghuang.oauth.dto.OauthClientCreateDTO;
import org.dinghuang.oauth.dto.OauthClientUpdateDTO;
import org.dinghuang.oauth.dto.OauthClientCreateOrUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.mybatis.model.Pageable;

import java.util.List;

/**
 * 认证客户端Mapper
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/09
 */
@Mapper
public interface OauthClientMapper {

    OauthClientMapper INSTANCE = Mappers.getMapper(OauthClientMapper.class);

    /**
     * createDto到do的转换
     *
     * @param oauthClientCreateDTO oauthClientCreateDTO
     * @return OauthClientDO
     */
    OauthClientDO createDtoToDo(OauthClientCreateDTO oauthClientCreateDTO);

    /**
     * updateDto到do的转换
     *
     * @param oauthClientUpdateDTO oauthClientUpdateDTO
     * @return OauthClientDO
     */
    OauthClientDO updateDtoToDo(OauthClientUpdateDTO oauthClientUpdateDTO);

    /**
     * createOrUpdateDto到dto的转换
     *
     * @param oauthClientCreateOrUpdateDTO oauthClientCreateOrUpdateDTO
     * @return OauthClientDO
     */
    OauthClientDO createOrUpdateDtoToDo(OauthClientCreateOrUpdateDTO oauthClientCreateOrUpdateDTO);

    /**
     * do到dto的转换
     *
     * @param oauthClientDO oauthClientDO
     * @return OauthClientDTO
     */
    OauthClientDTO doToDto(OauthClientDO oauthClientDO);

    /**
     * do到dto列表转换
     *
     * @param oauthClientDOS oauthClientDOS
     * @return OauthClientDTO
     */
    List<OauthClientDTO> doToDtos(List<OauthClientDO> oauthClientDOS);

    /**
     * do到dto分页转换
     *
     * @param oauthClientDOPageable oauthClientDOPageable
     * @return OauthClientDTO
     */
    PageableDTO<OauthClientDTO> pageDoToDto(Pageable<OauthClientDO> oauthClientDOPageable);
}