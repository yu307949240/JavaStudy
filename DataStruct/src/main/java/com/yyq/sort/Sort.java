package com.yyq.sort;

public abstract class Sort<T extends Comparable<T>> {
    abstract void sort(T[] arr);

    // 比较v是否小于w
    protected boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    //交换数组中位置为i，j的元素
    public void swap(T[] arr, int i, int j) {
        T t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
