package org.dinghuang.demo.application.infra.repository;

import org.dinghuang.core.annotation.Repository;
import org.dinghuang.core.mybatis.BaseRepository;
import org.dinghuang.demo.application.infra.dataobject.OrderDO;

/**
 * 订单Repository
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Repository
public interface OrderRepository extends BaseRepository<OrderDO> {

}