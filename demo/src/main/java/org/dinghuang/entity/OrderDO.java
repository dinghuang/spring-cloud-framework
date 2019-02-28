package org.dinghuang.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.dinghuang.core.mybatis.model.BaseModel;


/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Data
@TableName("order")
public class OrderDO extends BaseModel {
    private Long id;
    private String customerName;
}
