package com.yyq.heap;

import com.yyq.sort.CommonUtils;

/**
 * 堆排序。
 * @author yyq
 * @since 2018/10/10
 */
public class HeapSort {

    public void sort(Comparable[] arr) {
        int n = arr.length;
        MaxHeap<Integer> maxHeap = new MaxHeap<Integer>(n);
        for (int i = 0; i < n; i++) {
            maxHeap.push((Integer) arr[i]);
        }

        for (int j = 0; j < n; j++) {
            System.out.println(maxHeap.pop());
        }
    }

    public static void main(String[] args) {
        Integer[] arr = CommonUtils.generateRandomArr(10,1,50);
        new HeapSort().sort(arr);
    }

}
