package org.dinghuang.core.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/28
 */
@Api(value = "Druid数据信息", tags = {"Druid数据信息"})
@RestController
@RequestMapping(value = "/v1/druids")
public class DruidStatController {

    @GetMapping("/stat")
    @ApiOperation(value = "获取所有druid数据源的监控数据")
    public Object druidStat() {
        // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}