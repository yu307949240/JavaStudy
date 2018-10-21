package com.yyq.num;

/**
 * 输入一个整数，输出对应二进制中1的个数
 *
 * @author yyq
 * @since 2018/10/21
 */
public class NumberOf1 {
    public int numberOf1(int n) {
        int num = 0;
        while (n != 0) {
            num++;
            n &= n - 1;
        }
        return num;
    }
}
