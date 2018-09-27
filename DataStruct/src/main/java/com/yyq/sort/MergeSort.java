package com.yyq.sort;

import java.util.Arrays;

/**
 * 归并排序，递归算法
 *
 * @author yyq
 * @since 18/09/27
 */
public class MergeSort<T extends Comparable<T>> extends Sort<T> {

    void sort(Comparable[] arr) {
        int n = arr.length;
        sort(arr, 0, n - 1);
    }

    // 递归使用归并排序，对arr[l...r]的范围的元素进行排序，注意左右都是闭区间
    public void sort(Comparable[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
//        if (right - left <= 15) {
//            insertSort(arr, left, right);
//            return;
//        }
        int mid = (left + right) / 2;
        sort(arr, left, mid);
        sort(arr, mid + 1, right);
        if (less(arr[mid + 1], arr[mid])) { // 归并排序优化
            merge(arr, left, mid, right);
        }
    }

    // 将arr[l...mid]和arr[mid+1...r]两部分进行归并排序
    public void merge(Comparable[] arr, int l, int mid, int r) {
        // 声明辅助数组，注意copyOfRange是左闭右开区间
        Comparable[] aux = Arrays.copyOfRange(arr, l, r + 1);

        // 指向子数组开头的位置
        int i = l;
        int j = mid + 1;
        for (int k = l; k <= r; k++) {
            if (i > mid) {
                arr[k] = aux[j - l];
                j++;
            } else if (j > r) {
                arr[k] = aux[i - l];
                i++;
            } else if (aux[i - l].compareTo(aux[j - l]) < 0) {
                arr[k] = aux[i - l];
                i++;
            } else {
                arr[k] = aux[j - l];
                j++;
            }
        }
    }

    /**
     * 利用插入排序，排序arr[l...r]
     * @param arr
     * @param l
     * @param r
     */
    public void insertSort(Comparable[] arr, int l, int r) {
        for (int i = l + 1; i < r; i++) {
            for (int j = i; j >= l && less(arr[j], arr[j - 1]); j--) {
                CommonUtils.swap(arr, j, j - 1);
            }
        }
    }

    public static void main(String[] args) {
        Integer[] arr = CommonUtils.generateRandomArr(40, 1, 100);
        new MergeSort<Integer>().sort(arr);
        CommonUtils.println(arr);
//        Integer arr[] = CommonUtils.generateRandomArr(10000, 1, 10000);
//        CommonUtils.testSort("com.yyq.sort.MergeSort",arr);
//
//        arr = CommonUtils.generateRandomArr(10000, 1, 10000);
//        CommonUtils.testSort("com.yyq.sort.InsertSort",arr);
    }
}
