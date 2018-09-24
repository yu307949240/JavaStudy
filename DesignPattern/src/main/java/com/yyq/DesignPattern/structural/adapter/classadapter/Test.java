package com.yyq.DesignPattern.structural.adapter.classadapter;

/**
 * 适配器模式-类适配器
 *
 * @author yyq
 * @since 18/09/16
 */
public class Test {
    public static void main(String[] args) {
        Target target = new ConcreteTarget();
        target.request();

        Target adapterTarget = new Adapter();
        adapterTarget.request();
    }
}

