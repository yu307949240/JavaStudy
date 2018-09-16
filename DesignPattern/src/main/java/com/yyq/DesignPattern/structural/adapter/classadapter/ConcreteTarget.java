package com.yyq.DesignPattern.structural.adapter.classadapter;

public class ConcreteTarget implements Target {
    @Override
    public void request() {
        System.out.println("concreteTarget目标方法");
    }
}
