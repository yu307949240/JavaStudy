package com.yyq.DesignPattern.structural.proxy.staticproxy;

import com.yyq.DesignPattern.structural.proxy.IOrderService;
import com.yyq.DesignPattern.structural.proxy.Order;
import com.yyq.DesignPattern.structural.proxy.OrderServiceImpl;
import com.yyq.DesignPattern.structural.proxy.db.DataSourceContextHolder;

public class OrderServiceStaticProxy {
    private IOrderService iOrderService;

    /*public int saveOrder(Order order){
        beforeMethod();
        iOrderService = new OrderServiceImpl();
        int userId = order.getUserId();
        int dbRouter = userId % 2;
        System.out.println("静态代理分配到【DB"+dbRouter+"】处理数据");

        // todo 设置dataSource
        DataSourceContextHolder.setDBType(String.valueOf(dbRouter));
        afterMethod();
        return iOrderService.saveOrder(order);
    }*/

    public int saveOrder(Order order){
        beforeMethod(order);
        iOrderService = new OrderServiceImpl();
        int res = iOrderService.saveOrder(order);
        afterMethod();
        return res;
    }

    private void beforeMethod(Order order){
        int userId = order.getUserId();
        int dbRouter = userId % 2;
        System.out.println("静态代理分配到【DB"+dbRouter+"】处理数据");

        // todo 设置dataSource
        DataSourceContextHolder.setDBType(String.valueOf(dbRouter));
        System.out.println("静态代理 before code");
    }

    private void afterMethod(){
        System.out.println("静态代理 after code");
    }
}
