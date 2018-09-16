package com.yyq.DesignPattern.structural.facade;

public class PointsPaymentService {
    public boolean pay(PointGift pointGift){
        //扣减积分
        System.out.println("支付"+pointGift.getName()+" 积分成功");
        return true;
    }
}
