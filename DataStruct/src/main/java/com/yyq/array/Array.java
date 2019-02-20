package com.yyq.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 数组相关
 *
 * @author yyq
 * @since 2018/01/13
 */
public class Array {
    /**
     * 一个字符串是否包含另一个字符串
     */
    public boolean isContain(String A, String B) {
        int[] arr = new int[26];
        for (int i = 0; i < A.length(); i++) {
            arr[A.charAt(i) - 'A']++;
        }
        for (int i = 0; i < B.length(); i++) {
            int count = arr[B.charAt(i) - 'A']--;
            if (count < 0)
                return false;
        }
        return true;
    }
    /**
     * 合并区间 先借助首字母进行排序，然后在声明一个额外的stack，进行求解
     */

    /**
     * 盛水最多的容器
     */
    public int maxArea(int[] arr) {
        int left = 0, right = arr.length - 1, b = 0, h = 0, max = 0;
        while (left < right) {
            b = right - left;
            if (arr[left] < arr[right]) {
                h = arr[left++];
            } else {
                h = arr[right--];
            }
            max = Math.max(max, h * b);
        }
        return max;
    }

    /**
     * 跳跃游戏
     */
    int n = 1;

    public boolean jump(int[] arr) {
        for (int i = arr.length - 2; i >= 0; i++) {
            if (arr[i] >= n)
                n = 1;
            else
                n++;
            if (i == 0 && n > 1)
                return false;
        }
        return true;
    }

    /**
     * 定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。
     * 如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * 输入: [1,3,5,6], 5
     * 输出: 2
     */
    public int searchInsert(int[] arr, int tartget) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= tartget)
                return i;
        }
        return arr.length;
    }

    /**
     * 合并排序两个数组
     */
    public void merge(int[] arr1, int m, int[] arr2, int n) {
        int p = m-- + n-- - 1;
        while (m >= 0 && n >= 0) {
            arr1[p--] = arr1[m] > arr2[n] ? arr1[m--] : arr2[n--];
        }
        /*if (m > 0) {
            while (m >= 0)
                arr1[p--] = arr1[m--];
        }*/
        if (n > 0) {
            while (n >= 0)
                arr1[p--] = arr1[n--];
        }
    }

    /**
     * 数组原地去重
     */
    public int removeDuplicates(int[] arr) {
        if (arr.length == 0)
            return 0;
        int pos = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != arr[pos]) {
                pos++;
                arr[pos] = arr[i];
            }
        }
        return pos + 1;
    }

    /**
     * 移除指定元素
     */
    public int removeItem(int[] arr, int k) {
        int pos = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != k) {
                arr[pos++] = arr[i];
            }
        }
        return pos + 1;
    }

    /**
     * 缺失的第一个正数
     */
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
