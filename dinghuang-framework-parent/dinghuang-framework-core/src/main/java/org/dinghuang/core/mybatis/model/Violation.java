package org.dinghuang.core.mybatis.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/5
 */
@Data
@AllArgsConstructor
public class Violation implements Serializable {

    private static final long serialVersionUID = 8569050752246587169L;

    private final String message;

    private final Object bean;

    private final String property;

    private final Object value;
}
