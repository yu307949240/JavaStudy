package com.yyq.DesignPattern.creational.abstractfactory;

public class JavaVideo extends Video {
    @Override
    public void produce() {
        System.out.println("录制Java课程视频");
    }
}
