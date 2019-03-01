package org.dinghuang.core.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.dinghuang.core.mybatis.model.BaseModel;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/1
 */
@Data
@TableName("oauth_login_attempt_times")
public class BaseLoginAttemptTimes extends BaseModel {
    private Long userId;
    private Integer loginAttemptTimes;
}
