package com.yyq.sort;

/**
 * shell排序,是插入排序引申出来的排序算法,
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
                CommonUtils.print(arr);
                System.out.println();
                //System.out.println(h);
            }
            h = h / 3;
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {4,3,6,2,1,9,5,8,7};
        new ShellSort<Integer>().sort(arr);
    }
    /**
     * 1	3	6	2	4	9	5	8	7
     * 1	3	6	2	4	9	5	8	7
     * 1	3	5	2	4	9	6	8	7
     * 1	3	5	2	4	9	6	8	7
     * 1	3	5	2	4	9	6	8	7
     * 1	3	5	2	4	9	6	8	7
     * 1	3	5	2	4	9	6	8	7
     * 1	2	3	5	4	9	6	8	7
     * 1	2	3	4	5	9	6	8	7
     * 1	2	3	4	5	9	6	8	7
     * 1	2	3	4	5	6	9	8	7
     * 1	2	3	4	5	6	8	9	7
     * 1	2	3	4	5	6	7	8	9
     */

    /**
     * 3	4	6	2	1	9	5	8	7
     * 3	4	6	2	1	9	5	8	7
     * 2	3	4	6	1	9	5	8	7
     * 1	2	3	4	6	9	5	8	7
     * 1	2	3	4	6	9	5	8	7
     * 1	2	3	4	5	6	9	8	7
     * 1	2	3	4	5	6	8	9	7
     * 1	2	3	4	5	6	7	8	9
     */
}
