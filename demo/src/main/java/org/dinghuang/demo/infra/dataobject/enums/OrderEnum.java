package org.dinghuang.demo.infra.dataobject.enums;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/19
 */
public enum OrderEnum {

    CUSTOMER_NAME("customer_name"),
    DESCRIPTION("description");

    private String value;

    OrderEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
