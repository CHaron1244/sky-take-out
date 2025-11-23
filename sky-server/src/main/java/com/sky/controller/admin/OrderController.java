package com.sky.controller.admin;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin/order")
@RestController
@Slf4j
@Api(tags = "管理端订单管理")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("分页订单搜索")
    @RequestMapping(value = "/conditionSearch", method = RequestMethod.GET)
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO orderPageQueryDTO) {
        return Result.success(orderService.conditionSearch(orderPageQueryDTO));
    }

    @ApiOperation("各个状态的订单数量统计")
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public Result<OrderStatisticsVO> statistics() {
        OrderStatisticsVO orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    @ApiOperation("查询订单详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Result<Orders> detail(@PathVariable Long id) {
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }
}
