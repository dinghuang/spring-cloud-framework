package org.dinghuang.demo.infra.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import org.dinghuang.core.mybatis.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 测试对象DO
 *
 * @author dinghuang123@gmail.com
 * @since 2020/01/16
 */
@Data
@TableName("test")
@EqualsAndHashCode(callSuper = true)
public class TestDO extends BaseModel {

    /**
     * 主键
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;


}
