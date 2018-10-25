package com.yyq.num;

/**
 * 把一根绳子剪成多段，并且使得每段的长度乘积最大。最终剪成3和2是最大的；贪心算法
 *
 * @author yyq
 * @since 2018/10/23
 */
public class IntegerBreak {
    public int integerBreak(int n) {
        if (n < 2)
            return 0;
        if (n == 2)
            return 1;
        if (n == 3)
            return 2;
        int timesOf3 = n / 2;
        if (n - 3 * timesOf3 == 1) {
            timesOf3--;
        }
        int timesOf2 = (n - 3 * timesOf3) / 2;
        return (int) (Math.pow(timesOf3, 3) * Math.pow(timesOf2, 2));
    }
}
