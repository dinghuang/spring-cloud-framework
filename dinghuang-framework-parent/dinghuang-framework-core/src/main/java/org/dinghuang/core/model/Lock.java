package org.dinghuang.core.model;

import lombok.Data;
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

    public String getRemain() {
        return TimeUtils.getTimeDes(remainMillis);
    }

    public String getElapse() {
        return TimeUtils.getTimeDes(elapseMillis);
    }


}
