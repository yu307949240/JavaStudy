package com.yyq.java;

/**
 * static执行顺序：静态代码快-》非静态代码块-》构造方法
 * @author yyq
 * @since 18/09/15
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
        StaticTest.test();
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

    static class Parent{
        /**
         * 静态变量》静态代码块》变量》代码块》构造器
         */
        public static int A = 1;
        static{
            A = 2;
        }
        static class Sub extends Parent{
            public static int B = A;
        }

        public static void main(String[] args) {
            System.out.println(Sub.B);
        }
        /**
         * 父类静态变量>父类静态代码块>子类静态变量>子类静态代码块>子类main方法>父亲变量>父类初始化块>父类构造器>子类变量>子类初始化块>子类构造器
         */
    }
}
