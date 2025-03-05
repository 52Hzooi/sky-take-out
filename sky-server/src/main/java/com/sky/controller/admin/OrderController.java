package com.sky.controller.admin;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Api(tags = "订单相关接口")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/conditionSearch")
    @ApiOperation("订单条件查询")
    public Result<PageResult> list(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("订单条件查询：{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.pageQuery4Admin(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/statistics")
    @ApiOperation("各个状态的订单数")
    public Result<OrderStatisticsVO> statistics() {
        log.info("各个状态的订单数");
        OrderStatisticsVO orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    @GetMapping("/details/{id}")
    @ApiOperation("订单详情")
    public Result<OrderVO> details(@PathVariable Long id) {
        log.info("订单详情：{}", id);
        OrderVO orderVO = orderService.detail(id);
        return Result.success(orderVO);
    }

    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
        log.info("商家接单：{}", ordersConfirmDTO);
        orderService.confirm(ordersConfirmDTO);
        return Result.success();
    }

    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {
        log.info("商家拒单：{}", ordersRejectionDTO);
        orderService.rejection(ordersRejectionDTO);
        return Result.success();
    }

    @PutMapping("/cancel")
    @ApiOperation("订单取消")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) {
        log.info("订单取消:{}", ordersCancelDTO);
        orderService.cancel(ordersCancelDTO);
        return Result.success();
    }

    @PutMapping("/delivery/{id}")
    @ApiOperation("订单派送")
    public Result delivery(@PathVariable("id") Long id) {
        log.info("订单派送：{}", id);
        orderService.delivery(id);
        return Result.success();
    }

    @PutMapping("/complete/{id}")
    @ApiOperation("订单完成")
    public Result complete(@PathVariable("id") Long id) {
        log.info("订单完成：{}", id);
        orderService.complete(id);
        return Result.success();
    }
}
