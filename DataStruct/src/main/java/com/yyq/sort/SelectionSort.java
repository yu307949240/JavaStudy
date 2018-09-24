package com.yyq.sort;

/**
 * 选择排序，On2
 *
 * @author yyq
 * @since 18/09/24
 */
public class SelectionSort<T extends Comparable<T>> extends Sort<T> {

    /**
     *  算法类不允许产生任何实例，简单的单例模式，没有做同步
     */
    // private SelectionSort(){}

    /**
     * 选择排序，在数组中先找到最小元素，把最小元素和第一个元素交换位置，再从剩下的元素中找到最小的元素，将它与数组中第二个元素交换位置。不断的
     * 进行这样的操作，直到整个数组排序。
     *
     * @param arr
     * @param n
     */
    public void sort(Integer[] arr, int n) {
        for (int i = 0; i < n; i++) {
            // 寻找[i,n)区间内的最小值
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            CommonUtils.swap(arr, minIndex, i);
        }
    }

    public void sort(Comparable[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (less(arr[j], arr[minIndex])) {
                    minIndex = j;
                }
            }
            CommonUtils.swap(arr, minIndex, i);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Integer[] arr = CommonUtils.generateRandomArr(500, 1, 10000);
        new SelectionSort().sort(arr);
        CommonUtils.print(arr);
        CommonUtils.testSort("com.yyq.sort.SelectionSort", arr);
    }

}
