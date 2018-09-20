package com.yyq.DesignPattern.structural.proxy.dynamicproxy;

import com.yyq.DesignPattern.structural.proxy.IOrderService;
import com.yyq.DesignPattern.structural.proxy.Order;
import com.yyq.DesignPattern.structural.proxy.OrderServiceImpl;

public class Test {
    public static void main(String[] args) {
        Order order = new Order();
        order.setUserId(2);

        IOrderService orderService = (IOrderService) new OrderServiceDynamicProxy(order).bind();
        orderService.saveOrder(order);
    }
}
