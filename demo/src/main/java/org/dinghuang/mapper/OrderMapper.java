package org.dinghuang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.dinghuang.entity.OrderDO;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
}
