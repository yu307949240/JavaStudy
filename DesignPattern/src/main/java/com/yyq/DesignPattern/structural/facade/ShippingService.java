package com.yyq.DesignPattern.structural.facade;

public class ShippingService {
    public String shipGift(PointGift pointGift){
        //物流系统对接逻辑
        System.out.println(pointGift.getName()+"进入物流系统");
        String shippingOrderNo = "666";
        return shippingOrderNo;
    }
}
