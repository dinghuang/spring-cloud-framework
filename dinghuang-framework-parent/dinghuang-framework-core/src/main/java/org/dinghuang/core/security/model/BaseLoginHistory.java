package org.dinghuang.core.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.dinghuang.core.mybatis.model.BaseModel;

import java.util.Date;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/1
 */
@Data
@TableName("oauth_login_history")
public class BaseLoginHistory extends BaseModel {

    private Long userId;
    private Date lastLoginAt;
}
