package com.yyq.java;

/**
 * Java只支持值传递
 * @author yyq
 * @since 18/09/15
 */
public class StringPrint {

    public static void main(String[] args) {

        StringBuilder a = new StringBuilder("a");
        StringBuilder b = new StringBuilder("b");

        append(a,b);

        System.out.println(a + ", " + b); // abab, b

        b = a;

        System.out.println(a + ", " + b); // abab, abab
    }

    public static void append(StringBuilder a, StringBuilder b) {
        a.append(b);
        b = a;
        b.append(a);
    }
}
