package com.yyq.DesignPattern.structural.proxy;

public class OrderServiceImpl implements IOrderService {

    private IOrderDao iOrderDao;

    @Override
    public int saveOrder(Order order) {
        iOrderDao = new OrderDaoImpl();
        System.out.println("service层调用Dao层添加Order");
        return iOrderDao.insert(order);
    }
}
