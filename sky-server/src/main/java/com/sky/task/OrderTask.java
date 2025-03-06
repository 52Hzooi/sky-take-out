package com.sky.task;

import com.sky.constant.MessageConstant;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void processTimeoutOrder() {
        log.info("处理超时未支付订单:{}", LocalDateTime.now());
        // 每分钟处理当前超过15分钟未付款的订单
        // 现在时间 - 下单时间 > 15  ->  下单时间 < 现在时间 - 15
        // select * from orders where status = Orders.PENDING_PAYMENT and order_time < 现在时间 - 15
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> ordersList = orderMapper.getTimeoutOrder(Orders.PENDING_PAYMENT, time);
        if (ordersList != null && !ordersList.isEmpty()) {
            for (Orders order : ordersList) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason(MessageConstant.ORDER_TIME_OUT);
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void processDeliveryOrder() {
        log.info("处理超时处于派送中的订单：{}", LocalDateTime.now());
        // 每天1点整处理上一天还处于派送中的订单
        // 现在时间 - 订单时间 > 60  -> 订单时间 < 现在时间 - 60
        // select * from orders where status = Orders.DELIVERY_IN_PROGRESS and order_time < 现在时间 - 60
        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);
        List<Orders> ordersList = orderMapper.getTimeoutOrder(Orders.PENDING_PAYMENT, time);
        if (ordersList != null && !ordersList.isEmpty()) {
            for (Orders order : ordersList) {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }

}
