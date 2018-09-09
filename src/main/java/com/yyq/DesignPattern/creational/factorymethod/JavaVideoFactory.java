package com.yyq.DesignPattern.creational.factorymethod;


public class JavaVideoFactory extends VideoFactory {
    @Override
    public Video getVideo() {
        return new JavaVideo();
    }
}
