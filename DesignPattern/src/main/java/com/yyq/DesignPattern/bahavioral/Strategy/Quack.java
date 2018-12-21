package com.yyq.DesignPattern.bahavioral.Strategy;

public class Quack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Quack");
    }
}
