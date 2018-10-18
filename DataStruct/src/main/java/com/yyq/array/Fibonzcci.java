package com.yyq.array;

import sun.jvm.hotspot.oops.FieldType;

/**
 * 斐波那契数列相关
 *
 * @author yyq
 * @since 2018/10/16
 */
public class Fibonzcci {
    /**
     * 一个兔子一次可以跳1个或者2两个台阶，一共有n级台阶，一共可以跳多少次
     */
    public int jumpFloor(int n) {
        int[] res = {0, 1, 2};
        if (n <= 2) {
            return res[n];
        }
        return jumpFloor(n - 1) + jumpFloor(n - 2);
    }

    /**
     * 计算出N元人民币兑换成1元，2元和5元纸币的所有组合
     */
    public int sum(int N){
        int counter = 0,i,j,k;
        for(i=1;i<=N/1;i++){
            for(j=1;j<=N/2;j++){
                for(k=1;k<=N/5;k++){
                    if(i*1+j*2+k*5 == N){
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    public static void main(String[] args) {
        System.out.println(new Fibonzcci().sum(100));
    }

}
