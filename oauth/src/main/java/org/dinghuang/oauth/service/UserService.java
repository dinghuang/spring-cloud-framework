package org.dinghuang.oauth.service;

import org.dinghuang.oauth.dto.UserCreateDTO;
import org.dinghuang.oauth.dto.UserDTO;
import org.dinghuang.oauth.dto.UserUpdateDTO;
import org.dinghuang.oauth.dto.UserCreateOrUpdateDTO;
import org.dinghuang.oauth.infra.dataobject.UserDO;
import org.dinghuang.oauth.infra.repository.UserRepository;
import org.dinghuang.oauth.mapper.UserMapper;
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
 * 用户信息Service
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public UserDTO create(UserCreateDTO userCreateDTO) {
        UserDO userDO = UserMapper.INSTANCE.createDtoToDo(userCreateDTO);
        if (userRepository.insert(userDO) == 1) {
            return queryById(userDO.getId());
        } else {
            throw new InsertException("error.User.create");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public UserDTO update(UserUpdateDTO userUpdateDTO) {
        UserDO userDO = UserMapper.INSTANCE.updateDtoToDo(userUpdateDTO);
        if (userRepository.updateById(userDO) == 1) {
            return queryById(userDO.getId());
        } else {
            throw new UpdateException("error.User.update");
        }
    }

    public UserDTO queryById(Long id) {
        return UserMapper.INSTANCE.doToDto(userRepository.selectById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (userRepository.deleteById(id) != 1) {
            throw new DeleteException("error.User.deleteById");
        }
    }

    @SuppressWarnings("unchecked")
    public PageableDTO<UserDTO> queryByPage(PageableSearchDTO pageableSearchDTO) {
        Pageable<UserDO> userDOPageable = userRepository.selectPageCondition(PageableMapper.INSTANCE.searchDtoToDo(pageableSearchDTO), null);
        return UserMapper.INSTANCE.pageDoToDto(userDOPageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<UserDTO> batchCreateOrUpdate(List<UserCreateOrUpdateDTO> userCreateOrUpdateDTOS) {
        List<UserDTO> userDTOS = Collections.synchronizedList(new ArrayList<>(userCreateOrUpdateDTOS.size()));
        userCreateOrUpdateDTOS.forEach(userUpdateDTO -> {
            UserDO userDO = UserMapper.INSTANCE.createOrUpdateDtoToDo(userUpdateDTO);
            userRepository.insertOrUpdate(userDO);
            userDTOS.add(queryById(userDO.getId()));
        });
        return userDTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchDeleted(List<Long> ids) {
        ids.forEach(this::deleteById);
    }
}
