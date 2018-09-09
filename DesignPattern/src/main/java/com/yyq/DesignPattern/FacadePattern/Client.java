package com.yyq.DesignPattern.FacadePattern;

public class Client {
    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        computer.shutDown();
    }
}
