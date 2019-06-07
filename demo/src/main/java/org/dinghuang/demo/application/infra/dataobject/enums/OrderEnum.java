package org.dinghuang.demo.application.infra.dataobject.enums;

/**
 * 订单Enum
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
public enum OrderEnum {

    /**
     * 主键ID
     */
    ID("id"),

    /**
     * 名称
     */
    NAME("name"),

    /**
     * 订单名称
     */
    ORDER_NAME("order_name");

    private String value;

    OrderEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
