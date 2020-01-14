package org.dinghuang.oauth.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dinghuang.core.annotation.Repository;
import org.dinghuang.core.mybatis.BaseRepository;
import org.dinghuang.oauth.infra.dataobject.RoleDO;
import org.dinghuang.oauth.infra.dataobject.enums.RoleEnum;

import java.util.List;

/**
 * 角色Repository
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
@Repository
public interface RoleRepository extends BaseRepository<RoleDO> {

    default List<RoleDO> queryRolesByUserId(Long userId) {
        QueryWrapper<RoleDO> roleDOQueryWrapper = new QueryWrapper<>();
        roleDOQueryWrapper.eq(true, RoleEnum.USER_ID.value(), userId);
        return selectList(roleDOQueryWrapper);
    }
}