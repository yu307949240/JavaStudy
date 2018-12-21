package com.yyq.dp;

/**
 * 1、兔子跳台阶问题,递归和非递归解法
 * 2、(矩形覆盖)我们可以用 2*1 的小矩形横着或者竖着去覆盖更大的矩形。请问用 n 个 2*1 的小矩形无重叠地覆盖一个 2*n 的大矩形，总共有多少种方法？
 *
 * @author yyq
 * @since 2018/12/14
 */
public class JumpFloor {

    public static int jumpFloor(int n) {
        if (n <= 2)
            return n;
        int pre2 = 1, pre1 = 2;
        int cur = 1;
        for (int i = 2; i < n; i++) {
            cur = pre1 + pre2;
            pre2 = pre1;
            pre1 = cur;
        }
        return cur;
    }

    public static int jumpFloor1(int n) {
        if (n <= 2)
            return n;
        return jumpFloor1(n - 1) + jumpFloor1(n - 2);
    }

    public static void main(String[] args) {
        System.out.println(jumpFloor1(5));
        System.out.println(jumpFloor(5));
    }
}
