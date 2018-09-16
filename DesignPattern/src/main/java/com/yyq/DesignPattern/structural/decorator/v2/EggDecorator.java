package com.yyq.DesignPattern.structural.decorator.v2;

public class EggDecorator extends AbstractDecorator {
    public EggDecorator(AbstractBattercake abstractBattercake) {
        super(abstractBattercake);
    }

    @Override
    protected String getDesc() {
        return super.getDesc()+" 加一个鸡蛋";
    }

    @Override
    protected int cost() {
        return super.cost()+1;
    }
}
