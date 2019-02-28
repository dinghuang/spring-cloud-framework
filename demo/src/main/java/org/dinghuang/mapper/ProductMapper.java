package org.dinghuang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.dinghuang.entity.ProductDO;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Mapper
public interface ProductMapper extends BaseMapper<ProductDO> {
}
