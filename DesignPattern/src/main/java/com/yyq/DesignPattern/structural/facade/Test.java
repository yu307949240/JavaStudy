package com.yyq.DesignPattern.structural.facade;

public class Test {
    public static void main(String[] args) {
        PointGift pointGift = new PointGift("T恤");
        GiftExchangeService giftExchangeService = new GiftExchangeService();

        giftExchangeService.giftExchange(pointGift);
    }
}
