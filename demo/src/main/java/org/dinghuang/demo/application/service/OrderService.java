package org.dinghuang.demo.application.service;

import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.dto.PageableSearchDTO;
import org.dinghuang.core.exception.DeleteException;
import org.dinghuang.core.exception.InsertException;
import org.dinghuang.core.exception.UpdateException;
import org.dinghuang.core.mapper.PageableMapper;
import org.dinghuang.core.mybatis.model.Pageable;
import org.dinghuang.demo.application.dto.OrderCreateDTO;
import org.dinghuang.demo.application.dto.OrderCreateOrUpdateDTO;
import org.dinghuang.demo.application.dto.OrderDTO;
import org.dinghuang.demo.application.dto.OrderUpdateDTO;
import org.dinghuang.demo.application.infra.dataobject.OrderDO;
import org.dinghuang.demo.application.infra.repository.OrderRepository;
import org.dinghuang.demo.application.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 订单Service
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional(rollbackFor = Exception.class)
    public OrderDTO create(OrderCreateDTO orderCreateDTO) {
        OrderDO orderDO = OrderMapper.INSTANCE.createDtoToDo(orderCreateDTO);
        if (orderRepository.insert(orderDO) == 1) {
            return queryById(orderDO.getId());
        } else {
            throw new InsertException("error.Order.create");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public OrderDTO update(OrderUpdateDTO orderUpdateDTO) {
        OrderDO orderDO = OrderMapper.INSTANCE.updateDtoToDo(orderUpdateDTO);
        if (orderRepository.updateById(orderDO) == 1) {
            return queryById(orderDO.getId());
        } else {
            throw new UpdateException("error.Order.update");
        }
    }

    public OrderDTO queryById(Long id) {
        return OrderMapper.INSTANCE.doToDto(orderRepository.selectById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (orderRepository.deleteById(id) != 1) {
            throw new DeleteException("error.Order.deleteById");
        }
    }

    @SuppressWarnings("unchecked")
    public PageableDTO<OrderDTO> queryByPage(PageableSearchDTO pageableSearchDTO) {
        Pageable<OrderDO> orderDOPageable = orderRepository.selectPageCondition(PageableMapper.INSTANCE.searchDtoToDo(pageableSearchDTO), null);
        return OrderMapper.INSTANCE.pageDoToDto(orderDOPageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<OrderDTO> batchCreateOrUpdate(List<OrderCreateOrUpdateDTO> orderCreateOrUpdateDTOS) {
        List<OrderDTO> orderDTOS = Collections.synchronizedList(new ArrayList<>(orderCreateOrUpdateDTOS.size()));
        orderCreateOrUpdateDTOS.forEach(orderUpdateDTO -> {
            OrderDO orderDO = OrderMapper.INSTANCE.createOrUpdateDtoToDo(orderUpdateDTO);
            orderRepository.insertOrUpdate(orderDO);
            orderDTOS.add(queryById(orderDO.getId()));
        });
        return orderDTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchDeleted(List<Long> ids) {
        ids.forEach(this::deleteById);
    }
}
