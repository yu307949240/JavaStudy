package com.yyq.sort;

/**
 * 快排，双路快排，时间复杂度为NlogN，空间复杂度为logN
 * @author yyq
 * @since 18/09/28
 */
public class quickSort<T extends Comparable<T>> extends Sort<T> {


    /**
     * 单路快排
     */
    //返回p，使得arr[l...p-1] < arr[p] ; arr[p+1...r] > arr[p]
    int partition(Comparable[] arr, int l, int r) {

        int randomIndex = (int) (Math.random() * (r - l + 1) + l);
        System.out.println("randomIndex is ：" + randomIndex);
        CommonUtils.swap(arr, randomIndex, l);
        Integer v = (Integer) arr[l];
        //arr[l+1...j] < v ; arr[j+1...i) > v
        int j = l;
        for (int i = l + 1; i <= r; i++) {
            if (less(arr[i], v)) {
                CommonUtils.swap(arr, j + 1, i);
                j++;
            }
        }
        CommonUtils.swap(arr, l, j);
        return j;
    }

    void quickSort(Comparable[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        int p = partition(arr, l, r);
        quickSort(arr, l, p - 1);
        quickSort(arr, p + 1, r);
    }

    @Override
    void sort(T[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }


    /**
     * 双路快排
     */
    int partition2(Integer[] arr, int l, int r) {
        Integer v = arr[l];

        // arr[l+1...i] <= v ; arr[j...r] >= v
        int i = l + 1, j = r;
        while (true) {
            while (i <= r && arr[i] < v) i++;
            while (j >= l + 1 && arr[j] > v) j--;
            if (i > j) break;
            CommonUtils.swap(arr, i, j);
            i++;
            j--;
        }
        CommonUtils.swap(arr, l, j);
        return j;
    }

    void quickSort2(Comparable[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        int p = partition2((Integer[]) arr, l, r);
        quickSort2(arr, l, p - 1);
        quickSort2(arr, p + 1, r);
    }

    void sort2(T[] arr) {
        quickSort2(arr, 0, arr.length - 1);
    }

    public static void main(String[] args) {
        Integer[] arr = CommonUtils.generateRandomArr(10, 1, 30);
        new quickSort<Integer>().sort2(arr);
        CommonUtils.println(arr);
    }
}
