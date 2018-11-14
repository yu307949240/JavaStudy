package com.yyq.string;

/**
 * 正则表达式匹配
 *
 * @author yyq
 * @since 2018/11/08
 */
public class Rule {
    public static boolean isRule(String str) {
        if (str.isEmpty()) {
            return false;
        }
        return str.matches("[+-]?\\d*(\\.\\d+)?([eE][+-]?\\d+)?");
    }

    public static void main(String[] args) {
        System.out.println(isRule("+100"));
    }
}
