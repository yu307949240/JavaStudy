package com.yyq.num;

/**
 * 给出一个数组代表每天的股票金额，让你在最多买卖一次的情况下算出最大的收益额
 *
 * @author yyq
 * @since 2018/10/21
 */
public class MaxProfit {
    public int maxProfit(int[] prices) {
        int max = 0, minPrice = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice)
                minPrice = prices[i];
            int delta = prices[i] - minPrice;
            if (delta > max) max = delta;
        }
        return max;
    }
}
