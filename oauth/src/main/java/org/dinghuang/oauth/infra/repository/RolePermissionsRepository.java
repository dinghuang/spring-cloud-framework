package org.dinghuang.oauth.infra.repository;

import org.dinghuang.oauth.infra.dataobject.RolePermissionsDO;
import org.dinghuang.core.annotation.Repository;
import org.dinghuang.core.mybatis.BaseRepository;

/**
 * 角色权限Repository
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/08
 */
@Repository
public interface RolePermissionsRepository extends BaseRepository<RolePermissionsDO> {

}