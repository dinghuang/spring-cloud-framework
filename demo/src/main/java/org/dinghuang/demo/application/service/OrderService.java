package org.dinghuang.demo.application.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dinghuang.core.exception.CommonException;
import org.dinghuang.core.mybatis.model.enums.BaseModelEnum;
import org.dinghuang.demo.application.dto.OrderCreateDTO;
import org.dinghuang.demo.application.dto.OrderDTO;
import org.dinghuang.demo.application.dto.OrderUpdateDTO;
import org.dinghuang.demo.application.mapper.OrderMapper;
import org.dinghuang.demo.infra.dataobject.OrderDO;
import org.dinghuang.demo.infra.dataobject.enums.OrderEnum;
import org.dinghuang.demo.infra.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return OrderMapper.INSTANCE.doToDto(orderDO);
    }

    /**
     * 创建订单
     *
     * @param orderCreateDTO orderCreateDTO
     * @return OrderDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO create(OrderCreateDTO orderCreateDTO) {
        OrderDO orderDO = OrderMapper.INSTANCE.createToDo(orderCreateDTO);
        if (orderRepository.insert(orderDO) == 1) {
            return queryById(orderDO.getUuid());
        } else {
            throw new CommonException("error.order.insert");
        }
    }

    /**
     * 更新订单
     *
     * @param orderUpdateDTO orderUpdateDTO
     * @return OrderDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO update(OrderUpdateDTO orderUpdateDTO) {
        OrderDO orderDO = OrderMapper.INSTANCE.updateToDo(orderUpdateDTO);
        if (orderRepository.updateById(orderDO) == 1) {
            return queryById(orderDO.getUuid());
        } else {
            throw new CommonException("error.order.update");
        }
    }

    /**
     * 根据用户id查询所有订单
     *
     * @param userId userId
     * @return List
     */
    public List<OrderDTO> queryByUserId(Long userId) {
//        QueryWrapper<OrderDO> orderDOQueryWrapper = new QueryWrapper<>();
//        orderDOQueryWrapper.eq(true,BaseModelEnum.CREATE_USER.value(),userId)
//                .orderByDesc(BaseModelEnum.CREATE_DATE.value())
//                .eq(true,OrderEnum.CUSTOMER_NAME.value(),userId);
//        return OrderMapper.INSTANCE.doToDtos(orderRepository.selectList(orderDOQueryWrapper));
        return OrderMapper.INSTANCE.doToDtos(orderRepository.queryAllByUserId(userId));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (orderRepository.deleteById(id) != 1) {
            throw new CommonException("error.order.delete");
        }
    }
}
