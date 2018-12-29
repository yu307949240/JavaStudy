package com.yyq.java;

/**
 * String类的使用; “对String对象的任何改变都不影响到原对象，相关的任何change操作都会生成新的对象”。
 *
 * @author yyq
 * @since 2018/10/25
 */
public class StringTest {
    public static void main(String[] args) {
        String str1 = "hello word"; // 字面常量，保存在常量池中
        String str2 = new String("hello word"); // 会在堆中new一个对象 like '123' =123
        String str3 = "hello word";
        String str4 = new String("hello word");
        System.out.println(str1 == str3); // true
        System.out.println(str1 == str2); // false
        System.out.println(str2 == str4); // false
        String a = "hello2";
        String b = "hello" + 2;
        // "hello"+2在编译期间就已经被优化成"hello2"，因此在运行期间，变量a和变量b指向的是同一个对象。
        System.out.println(a == b); // true
        String c = "hello2";
        String d = "hello";
        String e = d + 2;
        // 由于有符号引用的存在，所以  String e = d + 2;不会在编译期间被优化，不会把d+2当做字面常量来处理的，因此这种方式生成的对象事实上是保存在堆上的。
        System.out.println(c == e); // false
        final String f = "hello";
        String g = f + 2;
        // 对于被final修饰的变量，会在class文件常量池中保存一个副本，也就是说不会通过连接而进行访问，对final变量的访问在编译期间都会直接被替代为真实的值。
        // 那么String g = f + 2;在编译期间就会被优化成：String c = "hello" + 2;
        System.out.println(c == g); // true
    }
}
