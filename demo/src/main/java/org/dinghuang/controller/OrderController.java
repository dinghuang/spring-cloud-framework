package org.dinghuang.controller;

import org.dinghuang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@RestController("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders/{id}/total_amount")
    public String calcOrderTotalAmount(@PathVariable Long id) {
        return orderService.calcTotalAmount(id).toString();
    }
}
