package com.yyq.DesignPattern.bahavioral.Strategy;

public class Ecack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("Ecack");
    }
}
