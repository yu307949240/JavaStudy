package com.yyq.java;

/**
 * static执行顺序：静态代码快-》非静态代码块-》构造方法
 */
public class StaticTest {
    public StaticTest(){
        System.out.println("默认构造方法！");
    }
    {
        System.out.println("非静态代码块！");
    }
    static{
        System.out.println("静态代码块！");
    }

    public static void test(){
        System.out.println("静态方法中的内容！");
        {
            System.out.println("静态方法中的代码块！");
        }
    }

    public static void main(String[] args) {
        //StaticTest.test();
        /*
         静态代码块！
         静态方法中的内容！
         静态方法中的代码块！
         */

        StaticTest staticTest = new StaticTest();
        /*
         静态代码块！
         非静态代码块！
         默认构造方法！
         */
    }
}
