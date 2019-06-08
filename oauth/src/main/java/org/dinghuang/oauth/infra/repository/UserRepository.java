package org.dinghuang.oauth.infra.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dinghuang.core.annotation.Repository;
import org.dinghuang.core.mybatis.BaseRepository;
import org.dinghuang.oauth.infra.dataobject.UserDO;
import org.dinghuang.oauth.infra.dataobject.enums.UserEnum;

/**
 * 用户信息Repository
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Repository
public interface UserRepository extends BaseRepository<UserDO> {

    default UserDO queryByName(String name) {
        QueryWrapper<UserDO> userDOQueryWrapper = new QueryWrapper<>();
        userDOQueryWrapper.eq(true, UserEnum.ACCOUNT.value(), name);
        return selectOne(userDOQueryWrapper);
    }

}