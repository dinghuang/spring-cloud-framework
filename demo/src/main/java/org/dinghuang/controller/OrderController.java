package org.dinghuang.controller;

import io.swagger.annotations.*;
import org.dinghuang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
@Api(value = "User", tags = {"User"})
@RestController("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders/{id}/total_amount")
    @ApiOperation(value = "使用ID查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "ID", name = "id", dataType = "int", paramType = "path", required = true, defaultValue = "1")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "请求参数有误"),
            @ApiResponse(code = 401, message = "未授权"),
            @ApiResponse(code = 403, message = "禁止访问"),
            @ApiResponse(code = 404, message = "请求路径不存在"),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    public String calcOrderTotalAmount(@PathVariable Long id) {
        return orderService.calcTotalAmount(id).toString();
    }
}
