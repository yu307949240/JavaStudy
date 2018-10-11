package com.yyq.heap;

import com.yyq.sort.CommonUtils;

/**
 * 优化的堆排序，时间复杂度为NlogN，不需要开辟额外的空间，空间复杂度为O(1)
 *
 * @author yyq
 * @since 2018/10/11
 */
public class HeapSort2<T extends Comparable<T>> {
    public void sort(T[] arr, int n) {
        // heapify 构建堆
        for (int i = (n - 1) / 2; i >= 0; i--) {
            shiftDown(arr, n, i);
        }
        // 交换首尾元素，交换之后在进行heapify操作，每次循环--
        for (int i = n - 1; i > 0; i--) {
            CommonUtils.swap(arr, 0, i);
            shiftDown(arr, i, 0);
        }
    }

    // 对arr[]中索引位置位i的元素进行shiftDown操作
    private void shiftDown(T[] arr, int n, int index) {
        while (2 * index + 1 < n) {
            int k = 2 * index + 1;
            if ((k + 1 < n) && (arr[k].compareTo(arr[k + 1]) < 0))
                k = k + 1;
            if (arr[index].compareTo(arr[k]) >= 0)
                break;
            CommonUtils.swap(arr, k, index);
            index = k;
        }
    }

    public static void main(String[] args) {
        Integer[] arr = CommonUtils.generateRandomArr(10, 1, 50);
        new HeapSort2<Integer>().sort(arr, 10);
        CommonUtils.println(arr);
    }
}
