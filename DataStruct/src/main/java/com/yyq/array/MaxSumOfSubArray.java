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
        int maxHere, maxSum;
        maxHere = maxSum = a[0];
        for (int val : a) {
            if (maxHere < 0)
                maxHere = val;
            else
                maxHere += val;
            maxSum = Math.max(maxSum, maxHere);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        int arr[] = {6, -3, -2, 7, -15, 1, 2, 2};
        System.out.println(maxSumOfSubArray(arr));
    }
}
