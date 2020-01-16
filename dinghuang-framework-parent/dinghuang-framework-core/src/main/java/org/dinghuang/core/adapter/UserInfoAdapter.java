package org.dinghuang.core.adapter;

import org.dinghuang.core.model.UserDO;

/**
 * @author dinghuang123@gmail.com
 * @since 2020/1/16
 */
public interface UserInfoAdapter {

    /**
     * 获取用户信息
     *
     * @return UserDO
     */
    UserDO getUser();
}
