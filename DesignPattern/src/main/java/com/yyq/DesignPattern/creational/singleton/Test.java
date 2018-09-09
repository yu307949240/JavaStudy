package com.yyq.DesignPattern.creational.singleton;

public class Test {
    public static void main(String[] args) {
       // LazySingleton lazySingleton = LazySingleton.getInstance();
        Thread t1 = new Thread(new T());
        Thread t2 = new Thread(new T());
        t1.start();
        t2.start();
        System.out.println("Program end");
    }
}
