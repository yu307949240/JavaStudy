package com.yyq.DesignPattern.structural.adapter.objectadapter.example;

/**
 * 适配器模式-对象适配器
 * @author yyq
 * @since 18/09/16
 */
public class Test {
    public static void main(String[] args) {
        DC5 dc5 = new PowerAdapter();
        dc5.outputDC5V();
    }
}
