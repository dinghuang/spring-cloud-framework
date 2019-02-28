package org.dinghuang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.dinghuang.entity.OrderLineDO;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Mapper
public interface OrderLineMapper extends BaseMapper<OrderLineDO> {
}
