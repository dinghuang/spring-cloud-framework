package org.dinghuang.oauth.service;

import org.dinghuang.oauth.dto.RoleCreateDTO;
import org.dinghuang.oauth.dto.RoleDTO;
import org.dinghuang.oauth.dto.RoleUpdateDTO;
import org.dinghuang.oauth.dto.RoleCreateOrUpdateDTO;
import org.dinghuang.oauth.infra.dataobject.RoleDO;
import org.dinghuang.oauth.infra.repository.RoleRepository;
import org.dinghuang.oauth.mapper.RoleMapper;
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
 * 角色Service
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    public RoleDTO create(RoleCreateDTO roleCreateDTO) {
        RoleDO roleDO = RoleMapper.INSTANCE.createDtoToDo(roleCreateDTO);
        if (roleRepository.insert(roleDO) == 1) {
            return queryById(roleDO.getId());
        } else {
            throw new InsertException("error.Role.create");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public RoleDTO update(RoleUpdateDTO roleUpdateDTO) {
        RoleDO roleDO = RoleMapper.INSTANCE.updateDtoToDo(roleUpdateDTO);
        if (roleRepository.updateById(roleDO) == 1) {
            return queryById(roleDO.getId());
        } else {
            throw new UpdateException("error.Role.update");
        }
    }

    public RoleDTO queryById(Long id) {
        return RoleMapper.INSTANCE.doToDto(roleRepository.selectById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (roleRepository.deleteById(id) != 1) {
            throw new DeleteException("error.Role.deleteById");
        }
    }

    @SuppressWarnings("unchecked")
    public PageableDTO<RoleDTO> queryByPage(PageableSearchDTO pageableSearchDTO) {
        Pageable<RoleDO> roleDOPageable = roleRepository.selectPageCondition(PageableMapper.INSTANCE.searchDtoToDo(pageableSearchDTO), null);
        return RoleMapper.INSTANCE.pageDoToDto(roleDOPageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<RoleDTO> batchCreateOrUpdate(List<RoleCreateOrUpdateDTO> roleCreateOrUpdateDTOS) {
        List<RoleDTO> roleDTOS = Collections.synchronizedList(new ArrayList<>(roleCreateOrUpdateDTOS.size()));
        roleCreateOrUpdateDTOS.forEach(roleUpdateDTO -> {
            RoleDO roleDO = RoleMapper.INSTANCE.createOrUpdateDtoToDo(roleUpdateDTO);
            roleRepository.insertOrUpdate(roleDO);
            roleDTOS.add(queryById(roleDO.getId()));
        });
        return roleDTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchDeleted(List<Long> ids) {
        ids.forEach(this::deleteById);
    }
}
