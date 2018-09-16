package com.yyq.DesignPattern.structural.adapter.objectadapter;

public class Adapter implements Target {

    Adaptee adaptee = new Adaptee();
    @Override
    public void request() {
        adaptee.adapteeRequest();
    }
}
