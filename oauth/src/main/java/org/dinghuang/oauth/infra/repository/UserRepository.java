package org.dinghuang.oauth.infra.repository;

import org.dinghuang.oauth.infra.dataobject.UserDO;
import org.dinghuang.core.annotation.Repository;
import org.dinghuang.core.mybatis.BaseRepository;

/**
 * 用户信息Repository
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Repository
public interface UserRepository extends BaseRepository<UserDO> {

}