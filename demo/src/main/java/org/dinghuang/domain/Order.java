package org.dinghuang.domain;


import lombok.Data;
import org.dinghuang.core.utils.ConvertorUtils;
import org.dinghuang.entity.OrderDO;
import org.dinghuang.entity.OrderLineDO;
import org.dinghuang.entity.ProductDO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Data
public class Order {
    private OrderDO orderHead;
    private List<OrderLine> orderlines;

    @Data
    static class OrderLine {
        private OrderLineDO orderLinedo;
        private ProductDO product;

        public OrderLine doToDomain(OrderLineDO orderLine, ProductDO productDO) {
            OrderLine ol = new OrderLine();
            ol.setOrderLinedo(ConvertorUtils.toTarget(orderLine, OrderLineDO.class));
            ol.setProduct(ConvertorUtils.toTarget(productDO, ProductDO.class));
            return ol;
        }
    }

    /**
     * 获取订单总金额
     *
     * @return BigDecimal
     */
    public BigDecimal calcTotalAmount() {
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderLine orderLine : this.orderlines) {
            totalAmount = totalAmount.add(new BigDecimal(orderLine.orderLinedo.getQuantity() * orderLine.product.getPrice().intValue()));
        }
        return totalAmount;
    }

    public Order doToDomain(OrderDO orderDO, List<OrderLineDO> orderLineDOS, Map<Long, ProductDO> productDOMap) {

        Order order = new Order();
        order.setOrderHead(ConvertorUtils.toTarget(orderDO, OrderDO.class));
        List<OrderLine> orderLines = new ArrayList<>(orderLineDOS.size());
        orderLineDOS.forEach(orderLineDO -> {
            OrderLine orderLine = new OrderLine();
            orderLines.add(orderLine.doToDomain(orderLineDO, productDOMap.get(orderLineDO.getId())));
        });
        order.setOrderlines(orderLines);
        order.setOrderlines(ConvertorUtils.toTargetList(orderLineDOS, OrderLine.class));
        return order;
    }
}


