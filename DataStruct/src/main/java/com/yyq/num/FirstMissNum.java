package com.yyq.num;

import java.util.HashMap;
import java.util.Map;

/**
 * 缺失的第一个正数
 */
public class FirstMissNum {
    public int firstMissNum(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;
        for (int val : arr) {
            map.put(val, 1);
            max = Math.max(max, val);
        }

        for (int i = 1; i <= max; i++) {
            if (map.get(i) != 1) {
                return i;
            }
        }
        return max + 1;
    }
}
