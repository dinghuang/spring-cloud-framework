package org.dinghuang.oauth.infra.repository;

import org.dinghuang.oauth.infra.dataobject.OauthClientDO;
import org.dinghuang.core.annotation.Repository;
import org.dinghuang.core.mybatis.BaseRepository;

/**
 * 认证客户端Repository
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/09
 */
@Repository
public interface OauthClientRepository extends BaseRepository<OauthClientDO> {

}