package org.dinghuang.demo.infra.repository;

import org.apache.ibatis.annotations.Param;
import org.dinghuang.core.annotation.Repository;
import org.dinghuang.core.mybatis.BaseRepository;
import org.dinghuang.demo.infra.dataobject.OrderDO;

import java.util.List;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Repository
public interface OrderRepository extends BaseRepository<OrderDO> {

    /**
     * 根据用户名查所有
     *
     * @param userId userId
     * @return List
     */
    List<OrderDO> queryAllByUserId(@Param("userId") Long userId);

}
