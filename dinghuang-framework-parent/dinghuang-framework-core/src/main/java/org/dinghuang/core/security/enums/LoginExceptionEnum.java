package org.dinghuang.core.security.enums;

/**
 * @author superlee
 */
public enum LoginExceptionEnum {

    USER_NAME_NOT_FOUND("usernameNotFound"),

    USER_IS_NOT_ACTIVATED("userNotActive"),

    ACCOUNT_IS_LOCKED("accountLocked"),

    CAPTCHA_IS_NULL("captchaNull"),

    CAPTCHA_IS_WRONG("captchaWrong"),

    USERNAME_NOT_FOUND_OR_PASSWORD_IS_WRONG("usernameNotFoundOrPasswordIsWrong"),

    LDAP_IS_DISABLE("ldapIsDisable"),

    ORGANIZATION_NOT_EXIST("organizationNotExist"),

    ORGANIZATION_NOT_ENABLE("organizationNotEnable");

    private final String value;

    LoginExceptionEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
