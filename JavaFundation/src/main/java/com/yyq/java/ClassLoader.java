package com.yyq.java;

public class ClassLoader {
    static ClassLoader book = new ClassLoader();
    static {
        System.out.println("书的静态代码块");
    }
    {
        System.out.println("书的普通代码块");
    }
    public ClassLoader() {
        System.out.println("书的构造方法");
        System.out.println("price=" + price +",amount=" + amount);
    }
    public static void staticFunction(){
        System.out.println("书的静态方法");
    }
    int price = 110;
    static int amount = 112;
    public static void main(String[] args)
    {
        staticFunction();
    }
}
