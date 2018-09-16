package com.yyq.DesignPattern.structural.decorator.v2;

public abstract class AbstractDecorator extends AbstractBattercake{

    private AbstractBattercake abstractBattercake;

    public AbstractDecorator(AbstractBattercake abstractBattercake){
        this.abstractBattercake = abstractBattercake;
    }

    @Override
    protected String getDesc() {
        return this.abstractBattercake.getDesc();
    }

    @Override
    protected int cost() {
        return this.abstractBattercake.cost();
    }
}
