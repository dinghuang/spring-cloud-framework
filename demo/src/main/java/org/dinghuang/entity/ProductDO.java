package org.dinghuang.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */

@Data
@TableName("product")
public class ProductDO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
