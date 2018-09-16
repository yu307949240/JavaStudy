package com.yyq.DesignPattern.structural.facade;

public class GiftExchangeService {
    private QuaifyService quaifyService = new QuaifyService();
    private PointsPaymentService pointsPaymentService = new PointsPaymentService();
    private ShippingService shippingService = new ShippingService();

    public void setQuaifyService(QuaifyService quaifyService) {
        this.quaifyService = quaifyService;
    }

    public void setPointsPaymentService(PointsPaymentService pointsPaymentService) {
        this.pointsPaymentService = pointsPaymentService;
    }

    public void setShippingService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void giftExchange(PointGift pointGift){
        if(quaifyService.isAvailable(pointGift)){
            //资格校验通过
            if(pointsPaymentService.pay(pointGift)){
                //如果支付积分成功
                String shippingNo = shippingService.shipGift(pointGift);
                System.out.println("物流系统下单成功，订单号是："+shippingNo);
            }
        }
    }
}
