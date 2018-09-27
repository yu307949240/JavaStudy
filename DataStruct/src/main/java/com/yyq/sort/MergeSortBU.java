package com.yyq.sort;

/**
 * 自底向上的归归并排序算法
 *
 * @author yyq
 * @since 18/09/27
 */
public class MergeSortBU<T extends Comparable<T>> extends Sort<T> {
    @Override
    void sort(T[] arr) {
        int n = arr.length;
        for (int sz = 1; sz <= n; sz += sz) {
            // 关键点1：i+sz < n 是为了防止下面merge的时候越界（因为i+sz可能大于n了）
            for (int i = 0; i < n; i += sz + sz) {
                // 对arr[i..i+sz-1] 和 arr[i+sz...i+2*sz-1] 进行归并排序
                // 关键点2：最然前面保证了1+sz<n,但是1+sz+sz可能超过最大下标n-1
                new MergeSort<T>().merge(arr, i, i + sz - 1, Math.min(i + sz + sz - 1, n - 1));
            }
        }
    }

    public static void main(String[] args) {
        Comparable[] arr = CommonUtils.generateRandomArr(20, 1, 100);
//        new MergeSortBU<Integer>().sort((Integer[]) arr);
//        CommonUtils.println(arr);
       CommonUtils.testSort("com.yyq.sort.MergeSortBU",arr);
    }
}
