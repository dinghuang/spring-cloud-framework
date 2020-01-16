package org.dinghuang.demo.infra.dataobject.enums;

/**
 * 测试对象Enum
 *
 * @author dinghuang123@gmail.com
 * @since 2020/01/16
 */
public enum TestEnum {

    /**
     * 主键
     */
    ID("ID"),

    /**
     * 姓名
     */
    NAME("NAME"),

    /**
     * 性别
     */
    SEX("SEX");

    private String value;

    TestEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
