package com.yyq.array;

/**
 * 连续子数组的最大和;{6, -3, -2, 7, -15, 1, 2, 2}，连续子数组的最大和为 8（从第 0 个开始，到第 3 个为止）。
 *
 * @author yyq
 * @since 2018/10/24
 */
public class MaxSumOfSubArray {
    public static int maxSumOfSubArray(int[] a) {
        if (a == null || a.length == 0)
            return 0;
        int maxSum = Integer.MIN_VALUE;
        int sum = 0;
        for (int val : a) {
            sum = sum <= 0 ? val : sum + val;
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        int arr[] =  {6, -3, -2, 7, -15, 1, 2, 2};
        System.out.println(maxSumOfSubArray(arr));
    }
}
