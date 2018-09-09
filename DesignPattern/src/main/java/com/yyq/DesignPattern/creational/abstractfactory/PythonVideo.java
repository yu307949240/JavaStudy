package com.yyq.DesignPattern.creational.abstractfactory;

public class PythonVideo extends Video{
    @Override
    public void produce() {
        System.out.println("录制python课程");
    }
}
