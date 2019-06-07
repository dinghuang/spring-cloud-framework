package org.dinghuang.demo.application.controller;

import org.dinghuang.demo.application.dto.OrderCreateDTO;
import org.dinghuang.demo.application.dto.OrderDTO;
import org.dinghuang.demo.application.dto.OrderUpdateDTO;
import org.dinghuang.demo.application.dto.OrderCreateOrUpdateDTO;
import org.dinghuang.demo.application.service.OrderService;
import org.dinghuang.core.dto.ValidList;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.dto.PageableSearchDTO;
import org.dinghuang.core.exception.CommonException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * 订单Controller
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Api(value = "订单", tags = {"订单"})
@RestController
@RequestMapping(value = "/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping
    @ApiOperation(value = "创建订单")
    public ResponseEntity<OrderDTO> create(@ApiParam(value = "创建订单", required = true)
                                                   @Valid @RequestBody OrderCreateDTO orderCreateDTO) {
        return Optional.ofNullable(orderService.create(orderCreateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.Order.create"));
    }

    @PutMapping
    @ApiOperation(value = "更新订单")
    public ResponseEntity<OrderDTO> update(@ApiParam(value = "更新订单", required = true)
                                                   @Valid @RequestBody OrderUpdateDTO orderUpdateDTO) {
        return Optional.ofNullable(orderService.update(orderUpdateDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.Order.update"));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除订单")
    public void deleteById(@ApiParam(value = "主键", required = true)
                           @PathVariable(name = "id") Long id) {
        orderService.deleteById(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询订单")
    public ResponseEntity<OrderDTO> queryById(@ApiParam(value = "id", required = true)
                                                      @PathVariable(name = "id") Long id) {
        return Optional.ofNullable(orderService.queryById(id))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.Order.queryById"));
    }

    @GetMapping(value = "/query_by_page")
    @ApiOperation(value = "分页查询订单")
    public ResponseEntity<PageableDTO<OrderDTO>> queryByPage(@Valid PageableSearchDTO pageableSearchDTO) {
        return Optional.ofNullable(orderService.queryByPage(pageableSearchDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.Order.queryByPage"));
    }

    @PostMapping(value = "/batch_create_or_update")
    @ApiOperation(value = "批量创建、更新订单")
    public ResponseEntity<List<OrderDTO>> batchCreateOrUpdate(@ApiParam(value = "创建、更新订单列表", required = true)
                                                                        @Valid @RequestBody ValidList<OrderCreateOrUpdateDTO> orderCreateOrUpdateDTOS) {
        return Optional.ofNullable(orderService.batchCreateOrUpdate(orderCreateOrUpdateDTOS))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.Order.batchCreateOrUpdate"));
    }

    @DeleteMapping(value = "/batch_deleted")
    @ApiOperation(value = "批量删除订单")
    public void batchDeleted(@ApiParam(value = "批量删除订单id列表", required = true)
                             @Valid @RequestBody List<Long> ids) {
        orderService.batchDeleted(ids);
    }

}
