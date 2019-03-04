package org.dinghuang.security.service;

import org.dinghuang.security.exception.AuthenticationException;
import org.dinghuang.security.model.BaseLoginAttemptTimes;
import org.dinghuang.security.model.BaseLoginHistory;
import org.dinghuang.security.repository.BaseLoginAttemptTimesRepository;
import org.dinghuang.security.repository.BaseLoginHistoryRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/1
 */
@Component
public class LoginRecord {

    private BaseLoginAttemptTimesRepository baseLoginAttemptTimesRepository;

    private BaseLoginHistoryRepository baseLoginHistoryRepository;

    public LoginRecord(BaseLoginAttemptTimesRepository baseLoginAttemptTimesRepository, BaseLoginHistoryRepository baseLoginHistoryRepository) {
        this.baseLoginAttemptTimesRepository = baseLoginAttemptTimesRepository;
        this.baseLoginHistoryRepository = baseLoginHistoryRepository;
    }

    public void loginError(Long userId) {
        BaseLoginAttemptTimes baseLoginAttemptTimes = baseLoginAttemptTimesRepository.queryByUserId(userId);
        if (baseLoginAttemptTimes == null) {
            baseLoginAttemptTimes = new BaseLoginAttemptTimes();
            baseLoginAttemptTimes.setUserId(userId);
            baseLoginAttemptTimes.setLoginAttemptTimes(1);
            if (baseLoginAttemptTimesRepository.insert(baseLoginAttemptTimes) != 1) {
                throw new AuthenticationException("error.insert.loginAttempt");
            }
        } else {
            baseLoginAttemptTimes.setLoginAttemptTimes(baseLoginAttemptTimes.getLoginAttemptTimes() + 1);
            if (baseLoginAttemptTimesRepository.updateById(baseLoginAttemptTimes) != 1) {
                throw new AuthenticationException("error.update.loginAttempt");
            }
        }
    }

    public void loginSuccess(Long userId) {
        BaseLoginAttemptTimes baseLoginAttemptTimes = baseLoginAttemptTimesRepository.queryByUserId(userId);
        BaseLoginHistory baseLoginHistory = baseLoginHistoryRepository.queryByUserId(userId);
        if (baseLoginHistory == null) {
            baseLoginHistory = new BaseLoginHistory();
            baseLoginHistory.setUserId(userId);
            baseLoginHistory.setLastLoginAt(new Date());
            if (baseLoginHistoryRepository.insert(baseLoginHistory) != 1) {
                throw new AuthenticationException("error.insert.loginHistory");
            }
        } else {
            baseLoginHistory.setLastLoginAt(new Date());
            if (baseLoginHistoryRepository.updateById(baseLoginHistory) != 1) {
                throw new AuthenticationException("error.update.loginHistory");
            }
        }
        if (baseLoginAttemptTimes != null) {
            baseLoginAttemptTimes.setLoginAttemptTimes(0);
            if (baseLoginAttemptTimesRepository.updateById(baseLoginAttemptTimes) != 1) {
                throw new AuthenticationException("error.update.loginAttempt");
            }
        }
    }
}
