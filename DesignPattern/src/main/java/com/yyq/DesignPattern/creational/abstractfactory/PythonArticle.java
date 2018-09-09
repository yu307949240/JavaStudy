package com.yyq.DesignPattern.creational.abstractfactory;

public class PythonArticle extends Article {
    @Override
    public void produce() {
        System.out.println("编写python课程手记");
    }
}
