package com.yyq.DesignPattern.structural.decorator.v2;

public class Battercake extends AbstractBattercake {
    @Override
    protected String getDesc() {
        return "煎饼";
    }

    @Override
    protected int cost() {
        return 8;
    }
}
