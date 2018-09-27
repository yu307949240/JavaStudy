package com.yyq.sort;

/**
 * shell排序,是插入排序引申出来的排序算法
 *
 * @author yyq
 * @since 18/09/24
 */
public class ShellSort<T extends Comparable<T>> extends Sort<T> {
    void sort(T[] arr) {
        int n = arr.length; // 10
        int h = 1;
        while (h < n / 3) {
            h = 3 * h + 1; // 10
        }

        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(arr[j], arr[j - h]); j -= h) {
                    swap(arr, j, j - h);
                }
            }
            h = h / 3;
        }
    }

    public static void main(String[] args) {
        Integer[] arr = CommonUtils.generateRandomArr(10,1,30);
        new ShellSort<Integer>().sort(arr);
        CommonUtils.print(arr);
    }
}
