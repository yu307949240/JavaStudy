package com.yyq.dp;

/**
 * 礼物的最大价值
 *
 * @author yyq
 * @since 2018/01/12
 */
public class MaxValue {
    public int maxValue(int[][] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int rows = arr.length;
        int cols = arr[0].length;
        int[][] dp = new int[rows][cols];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                int left = 0;
                int up = 0;
                if (i > 0)
                    up = dp[i - 1][j];
                if (j > 0)
                    left = dp[i][j - 1];
                dp[i][j] = Math.max(up, left) + arr[i][j];
            }
        }
        return dp[rows - 1][cols - 1];
    }
}

