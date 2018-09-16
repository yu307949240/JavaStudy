package com.yyq.DesignPattern.structural.facade;

public class QuaifyService {
    public boolean isAvailable(PointGift pointGift){
        System.out.println("校验"+pointGift.getName()+" 积分资格通过，库存通过");
        return true;
    }
}
