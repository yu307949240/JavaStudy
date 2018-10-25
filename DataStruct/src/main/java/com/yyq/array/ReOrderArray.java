package com.yyq.array;

/**
 * 调整数组顺序使奇数位于偶数前面
 *
 * @author yyq
 * @since 2018/10/23
 */
public class ReOrderArray {
    public void reOrderArray(int[] nums) {
        // 奇数个数
        int odd = 0;
        for (int i : nums) {
            if (nums[i] % 2 == 1)
                odd++;
        }
        int[] copy = nums.clone();
        int i = 0, j = odd;
        for (int k : copy) {
            if (nums[k] % 2 == 1) {
                nums[i++] = nums[k];
            } else {
                nums[j++] = nums[k];
            }
        }
    }
}
