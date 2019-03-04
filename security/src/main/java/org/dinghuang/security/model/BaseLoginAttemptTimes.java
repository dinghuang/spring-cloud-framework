package org.dinghuang.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dinghuang.core.mybatis.model.BaseModel;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/1
 */
@Data
@TableName("oauth_login_attempt_times")
@EqualsAndHashCode(callSuper = true)
public class BaseLoginAttemptTimes extends BaseModel {
    private Long userId;
    private Integer loginAttemptTimes;
}
