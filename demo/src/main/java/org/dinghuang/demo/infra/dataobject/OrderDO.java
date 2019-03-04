package org.dinghuang.demo.infra.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dinghuang.core.mybatis.model.BaseModel;


/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Data
@TableName("test_order")
@EqualsAndHashCode(callSuper = true)
public class OrderDO extends BaseModel {

    private String customerName;
    private String description;
}
