package org.dinghuang.core.utils;

import org.dinghuang.core.adapter.UserInfoAdapter;
import org.dinghuang.core.model.UserDO;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/5/15
 */
public class UserUtils {

    /**
     * 获取用户信息
     *
     * @return UserDO
     */
    public static UserDO getUser() {
        UserInfoAdapter userInfoAdapter = (UserInfoAdapter) SpringContextUtils.getContext().getBean("userInfoAdapter");
        return userInfoAdapter.getUser();
    }

}
