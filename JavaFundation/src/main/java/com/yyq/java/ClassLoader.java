package com.yyq.java;

/**
 * 类加载中初始化顺序：
 * 1、遇到 new、getstatic、putstatic、invokestatic
 * 这四条字节码指令时，如果类没有进行过初始化，则需要先触发其初始化。生成这4条指令的最常见的Java代码场景是：
 * 使用new关键字实例化对象的时候、读取或设置一个类的静态字段（被final修饰、已在编译器把结果放入常量池的静态字段除外）的时候，以及调用一个类的静态方法的时候。
 * 2、使用 java.lang.reflect 包的方法对类进行反射调用的时候，如果类没有进行过初始化，则需要先触发其初始化。
 * 3、当初始化一个类的时候，如果发现其父类还没有进行过初始化，则需要先触发其父类的初始化。
 * 4、当虚拟机启动时，用户需要指定一个要执行的主类（包含main()方法的那个类），虚拟机会先初始化这个主类。
 * 5、当使用 JDK1.7 动态语言支持时，如果一个 java.lang.invoke.MethodHandle实例最后的解析结果 REF_getstatic,REF_putstatic,REF_invokeStatic 的方法句柄，并且这个方法句柄所对应的类没有进行初始化，则需要先出触发其初始化。
 *
 */
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
        System.out.println("amount=" + amount);
    }
    int price = 110;
    static int amount = 112;
    public static void main(String[] args)
    {
        staticFunction();
    }
}
