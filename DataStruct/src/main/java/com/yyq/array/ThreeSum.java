package com.yyq.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 3sum问题
 *
 * @author yyq
 * @since 2018/10/21
 */
public class ThreeSum {
    public List<List<Integer>> threeSum(int[] arr, int target) {
        Arrays.sort(arr);
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        for (int i = 0; i < arr.length - 2; i++) {
            if (i > 0 && arr[i] == arr[i - 1])
                continue;
            for (int j = i + 1, k = arr.length - 1; j < k; ) {
                int sum = arr[i] + arr[j] + arr[k];
                if (sum == target) {
                    ret.add(Arrays.asList(arr[i], arr[j], arr[k]));
                    j++;
                    k--;
                    while (j < k && arr[j] == arr[j - 1]) j++;
                    while (j < k && arr[k] == arr[k - 1]) k++;
                } else if (sum < target)
                    k--;
                else
                    j++;
            }
        }
        return ret;
    }
}
