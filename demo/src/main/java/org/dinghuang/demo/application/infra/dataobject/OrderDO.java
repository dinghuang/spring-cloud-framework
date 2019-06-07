package org.dinghuang.demo.application.infra.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import org.dinghuang.core.mybatis.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单DO
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Data
@TableName("test_order")
@EqualsAndHashCode(callSuper = true)
public class OrderDO extends BaseModel {

    /**
     * 主键ID
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 订单名称
     */
    private String orderName;


}
