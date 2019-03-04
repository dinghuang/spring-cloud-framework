package org.dinghuang.demo.application.dto;

import lombok.Data;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
@Data
public class OrderDTO {
    private Long uuid;
    private String customerName;
    private String description;
    private Integer version;
}
