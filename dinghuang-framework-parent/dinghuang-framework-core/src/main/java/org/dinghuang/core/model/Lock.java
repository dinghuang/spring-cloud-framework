package org.dinghuang.core.model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.dinghuang.core.security.enums.UserEnum;
import org.dinghuang.core.security.model.User;
import org.dinghuang.core.security.repository.UserRepository;
import org.dinghuang.core.utils.SpringContextUtils;
import org.dinghuang.core.utils.TimeUtils;

import java.util.Date;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/26
 */
@Data
public class Lock {
    protected Boolean isLocked;
    protected String lockUser;
    protected String lockUserName;
    protected Date lockDate;
    protected String lockKey;
    protected long remainMillis;
    protected long elapseMillis;

    public String getLockUserName() {
        if (StringUtils.isBlank(lockUser)) {
            return "";
        }
        UserRepository userRepository = (UserRepository) SpringContextUtils.getContext().getBean("userRepository");
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq(UserEnum.USERNAME.value(), lockUser);
        User user = userRepository.selectOne(userQueryWrapper);
        if (user != null) {
            return user.getRealName();
        }
        return "";
    }

    public String getRemain() {
        return TimeUtils.getTimeDes(remainMillis);
    }

    public String getElapse() {
        return TimeUtils.getTimeDes(elapseMillis);
    }


}
