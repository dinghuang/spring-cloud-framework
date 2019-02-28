package org.dinghuang.service.impl;

import org.dinghuang.domain.Order;
import org.dinghuang.entity.OrderDO;
import org.dinghuang.entity.OrderLineDO;
import org.dinghuang.entity.ProductDO;
import org.dinghuang.mapper.OrderLineMapper;
import org.dinghuang.mapper.OrderMapper;
import org.dinghuang.mapper.ProductMapper;
import org.dinghuang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderLineMapper orderLineMapper;
    @Autowired
    ProductMapper productMapper;

    @Override
    public BigDecimal calcTotalAmount(Long id) {
        OrderDO orderDO = orderMapper.selectById(id);
        Map<String, Object> searchParam = new HashMap<>(1);
        searchParam.put("id", orderDO.getId());
        List<OrderLineDO> orderLineDOS = orderLineMapper.selectByMap(searchParam);
        Map<String, Object> productSearchParam = new HashMap<>(1);
        productSearchParam.put("id", orderDO.getId());
        Map<Long, ProductDO> productDOMap = productMapper.selectByMap(productSearchParam).stream().collect(Collectors.toMap(ProductDO::getId, Function.identity()));
        Order order = new Order();
        order.doToDomain(orderDO, orderLineDOS, productDOMap);
        return order.calcTotalAmount();
    }
}
