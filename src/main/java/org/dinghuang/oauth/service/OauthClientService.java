package org.dinghuang.oauth.service;

import org.dinghuang.oauth.dto.OauthClientCreateDTO;
import org.dinghuang.oauth.dto.OauthClientDTO;
import org.dinghuang.oauth.dto.OauthClientUpdateDTO;
import org.dinghuang.oauth.dto.OauthClientCreateOrUpdateDTO;
import org.dinghuang.oauth.infra.dataobject.OauthClientDO;
import org.dinghuang.oauth.infra.repository.OauthClientRepository;
import org.dinghuang.oauth.mapper.OauthClientMapper;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.dto.PageableSearchDTO;
import org.dinghuang.core.exception.DeleteException;
import org.dinghuang.core.exception.InsertException;
import org.dinghuang.core.exception.UpdateException;
import org.dinghuang.core.mapper.PageableMapper;
import org.dinghuang.core.mybatis.model.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 认证客户端Service
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/09
 */
@Service
public class OauthClientService {

    @Autowired
    private OauthClientRepository oauthClientRepository;

    @Transactional(rollbackFor = Exception.class)
    public OauthClientDTO create(OauthClientCreateDTO oauthClientCreateDTO) {
        OauthClientDO oauthClientDO = OauthClientMapper.INSTANCE.createDtoToDo(oauthClientCreateDTO);
        if (oauthClientRepository.insert(oauthClientDO) == 1) {
            return queryById(oauthClientDO.getId());
        } else {
            throw new InsertException("error.OauthClient.create");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public OauthClientDTO update(OauthClientUpdateDTO oauthClientUpdateDTO) {
        OauthClientDO oauthClientDO = OauthClientMapper.INSTANCE.updateDtoToDo(oauthClientUpdateDTO);
        if (oauthClientRepository.updateById(oauthClientDO) == 1) {
            return queryById(oauthClientDO.getId());
        } else {
            throw new UpdateException("error.OauthClient.update");
        }
    }

    public OauthClientDTO queryById(Long id) {
        return OauthClientMapper.INSTANCE.doToDto(oauthClientRepository.selectById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (oauthClientRepository.deleteById(id) != 1) {
            throw new DeleteException("error.OauthClient.deleteById");
        }
    }

    @SuppressWarnings("unchecked")
    public PageableDTO<OauthClientDTO> queryByPage(PageableSearchDTO pageableSearchDTO) {
        Pageable<OauthClientDO> oauthClientDOPageable = oauthClientRepository.selectPageCondition(PageableMapper.INSTANCE.searchDtoToDo(pageableSearchDTO), null);
        return OauthClientMapper.INSTANCE.pageDoToDto(oauthClientDOPageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<OauthClientDTO> batchCreateOrUpdate(List<OauthClientCreateOrUpdateDTO> oauthClientCreateOrUpdateDTOS) {
        List<OauthClientDTO> oauthClientDTOS = Collections.synchronizedList(new ArrayList<>(oauthClientCreateOrUpdateDTOS.size()));
        oauthClientCreateOrUpdateDTOS.forEach(oauthClientUpdateDTO -> {
            OauthClientDO oauthClientDO = OauthClientMapper.INSTANCE.createOrUpdateDtoToDo(oauthClientUpdateDTO);
            oauthClientRepository.insertOrUpdate(oauthClientDO);
            oauthClientDTOS.add(queryById(oauthClientDO.getId()));
        });
        return oauthClientDTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchDeleted(List<Long> ids) {
        ids.forEach(this::deleteById);
    }
}
