package com.yyq.array;

import java.util.HashMap;

/**
 * 2sum问题
 *
 * @author yyq
 * @since 2018/10/21
 */
public class TwoSum {
    public int[] twoSum(int[] arr, int target) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i])) {
                return new int[]{(map.get(arr[i])), i};
            }
            map.put(target - arr[i], i);
        }
        return null;
    }
}
