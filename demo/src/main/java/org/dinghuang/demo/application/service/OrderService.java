package org.dinghuang.demo.application.service;

import org.dinghuang.demo.application.dto.OrderCreateDTO;
import org.dinghuang.demo.application.dto.OrderDTO;
import org.dinghuang.demo.application.dto.OrderUpdateDTO;
import org.dinghuang.demo.infra.dataobject.OrderDO;
import org.dinghuang.demo.infra.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.dinghuang.core.utils.ConvertorUtils.toTarget;
import static org.dinghuang.core.utils.ConvertorUtils.toTargetList;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 根据订单id查询订单
     *
     * @param id 订单id
     * @return 订单详情
     */
    public OrderDTO queryById(Long id) {
        OrderDO orderDO = orderRepository.selectById(id);
        return toTarget(orderDO, OrderDTO.class);
    }

    /**
     * 创建订单
     *
     * @param orderCreateDTO orderCreateDTO
     * @return OrderDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO create(OrderCreateDTO orderCreateDTO) {
        OrderDO orderDO = toTarget(orderCreateDTO, OrderDO.class);
        orderRepository.insert(orderDO);
        return queryById(orderDO.getUuid());
    }

    /**
     * 更新订单
     *
     * @param orderUpdateDTO orderUpdateDTO
     * @return OrderDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO update(OrderUpdateDTO orderUpdateDTO) {
        OrderDO orderDO = toTarget(orderUpdateDTO, OrderDO.class);
        orderRepository.updateById(orderDO);
        return queryById(orderDO.getUuid());
    }

    /**
     * 根据用户id查询所有订单
     *
     * @param userId userId
     * @return List
     */
    public List<OrderDTO> queryByUserId(Long userId) {
//        orderRepository.queryAll();
        return toTargetList(orderRepository.queryAllByUserId(userId), OrderDTO.class);
    }
}
