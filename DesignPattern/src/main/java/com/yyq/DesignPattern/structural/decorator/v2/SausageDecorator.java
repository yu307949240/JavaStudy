package com.yyq.DesignPattern.structural.decorator.v2;

public class SausageDecorator extends AbstractDecorator {
    public SausageDecorator(AbstractBattercake abstractBattercake) {
        super(abstractBattercake);
    }

    @Override
    protected String getDesc() {
        return super.getDesc()+" 加一根香肠";
    }

    @Override
    protected int cost() {
        return super.cost()+2;
    }
}
