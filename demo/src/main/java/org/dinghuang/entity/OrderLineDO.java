package org.dinghuang.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Data
@TableName("order_line")
public class OrderLineDO {
    private Long id;
    private String productId;
    private Integer quantity;
}
