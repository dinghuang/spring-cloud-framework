package org.dinghuang.demo.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.dinghuang.core.exception.CommonException;
import org.dinghuang.demo.application.dto.OrderCreateDTO;
import org.dinghuang.demo.application.dto.OrderDTO;
import org.dinghuang.demo.application.dto.OrderUpdateDTO;
import org.dinghuang.demo.application.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Api(value = "Order", tags = {"Order"})
@RestController
@RequestMapping(value = "orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping
    @ApiOperation(value = "创建订单")
    public ResponseEntity<OrderDTO> create(@ApiParam(value = "创建订单对象", required = true)
                                           @Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        return Optional.ofNullable(orderService.create(orderCreateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.orders.create"));
    }

    @PutMapping
    @ApiOperation(value = "更新订单")
    public ResponseEntity<OrderDTO> update(@ApiParam(value = "订单更新对象", required = true)
                                           @Valid @RequestBody OrderUpdateDTO orderUpdateDTO) {
        return Optional.ofNullable(orderService.update(orderUpdateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.orders.update"));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据订单id查询订单信息")
    public ResponseEntity<OrderDTO> queryById(@ApiParam(value = "订单id", required = true)
                                              @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(orderService.queryById(id), HttpStatus.OK);
    }

    @GetMapping("/query_by_user/{userId}")
    @ApiOperation(value = "根据订单id查询订单信息")
    public ResponseEntity<List<OrderDTO>> queryByUserId(@ApiParam(value = "用户id", required = true)
                                                        @PathVariable(name = "userId") Long userId) {
        return new ResponseEntity<>(orderService.queryByUserId(userId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据订单id查询订单信息")
    public void deleteById(@ApiParam(value = "主键", required = true)
                                                        @PathVariable(name = "id") Long id) {
       orderService.deleteById(id);
    }
}
