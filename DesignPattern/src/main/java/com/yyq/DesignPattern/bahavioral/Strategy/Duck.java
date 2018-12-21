package com.yyq.DesignPattern.bahavioral.Strategy;

public class Duck {
    private QuackBehavior quackBehavior;

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

    public void confirm(){
        if(quackBehavior!=null){
            quackBehavior.quack();
        }
    }

    public static void main(String[] args) {
        Duck duck = new Duck();
        duck.setQuackBehavior(new Quack());
        duck.confirm();
    }
}
