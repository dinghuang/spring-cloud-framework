package org.dinghuang.oauth.service;

import org.dinghuang.oauth.dto.RolePermissionsCreateDTO;
import org.dinghuang.oauth.dto.RolePermissionsDTO;
import org.dinghuang.oauth.dto.RolePermissionsUpdateDTO;
import org.dinghuang.oauth.dto.RolePermissionsCreateOrUpdateDTO;
import org.dinghuang.oauth.infra.dataobject.RolePermissionsDO;
import org.dinghuang.oauth.infra.repository.RolePermissionsRepository;
import org.dinghuang.oauth.mapper.RolePermissionsMapper;
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
 * 角色权限Service
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
@Service
public class RolePermissionsService {

    @Autowired
    private RolePermissionsRepository rolePermissionsRepository;

    @Transactional(rollbackFor = Exception.class)
    public RolePermissionsDTO create(RolePermissionsCreateDTO rolePermissionsCreateDTO) {
        RolePermissionsDO rolePermissionsDO = RolePermissionsMapper.INSTANCE.createDtoToDo(rolePermissionsCreateDTO);
        if (rolePermissionsRepository.insert(rolePermissionsDO) == 1) {
            return queryById(rolePermissionsDO.getId());
        } else {
            throw new InsertException("error.RolePermissions.create");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public RolePermissionsDTO update(RolePermissionsUpdateDTO rolePermissionsUpdateDTO) {
        RolePermissionsDO rolePermissionsDO = RolePermissionsMapper.INSTANCE.updateDtoToDo(rolePermissionsUpdateDTO);
        if (rolePermissionsRepository.updateById(rolePermissionsDO) == 1) {
            return queryById(rolePermissionsDO.getId());
        } else {
            throw new UpdateException("error.RolePermissions.update");
        }
    }

    public RolePermissionsDTO queryById(Long id) {
        return RolePermissionsMapper.INSTANCE.doToDto(rolePermissionsRepository.selectById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (rolePermissionsRepository.deleteById(id) != 1) {
            throw new DeleteException("error.RolePermissions.deleteById");
        }
    }

    @SuppressWarnings("unchecked")
    public PageableDTO<RolePermissionsDTO> queryByPage(PageableSearchDTO pageableSearchDTO) {
        Pageable<RolePermissionsDO> rolePermissionsDOPageable = rolePermissionsRepository.selectPageCondition(PageableMapper.INSTANCE.searchDtoToDo(pageableSearchDTO), null);
        return RolePermissionsMapper.INSTANCE.pageDoToDto(rolePermissionsDOPageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<RolePermissionsDTO> batchCreateOrUpdate(List<RolePermissionsCreateOrUpdateDTO> rolePermissionsCreateOrUpdateDTOS) {
        List<RolePermissionsDTO> rolePermissionsDTOS = Collections.synchronizedList(new ArrayList<>(rolePermissionsCreateOrUpdateDTOS.size()));
        rolePermissionsCreateOrUpdateDTOS.forEach(rolePermissionsUpdateDTO -> {
            RolePermissionsDO rolePermissionsDO = RolePermissionsMapper.INSTANCE.createOrUpdateDtoToDo(rolePermissionsUpdateDTO);
            rolePermissionsRepository.insertOrUpdate(rolePermissionsDO);
            rolePermissionsDTOS.add(queryById(rolePermissionsDO.getId()));
        });
        return rolePermissionsDTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchDeleted(List<Long> ids) {
        ids.forEach(this::deleteById);
    }
}
