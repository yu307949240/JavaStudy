package com.yyq.sort;

/**
 * 插入排序。On2,每次都将当前元素插入到左侧已经排序的数组中，使得插入之后左侧数组依然有序。
 *
 * @author yyq
 * @since 18/09/24
 */
public class InsertSort<T extends Comparable<T>> extends Sort<T> {
    public void sort(T[] arr) {
        int n = arr.length;
        /*for (int i = 1; i < n; i++) {
            //寻找arr[i]插入合适的位置
            for (int j = i; j > 0; j--) {
                if(less(arr[j],arr[j-1])){
                    swap(arr,j,j-1);
                }else{
                    break;
                }
            }
        }*/
        for (int i = 1; i < n; i++) {
            //寻找arr[i]插入合适的位置
            for (int j = i; j > 0 && less(arr[j], arr[j - 1]); j--) {
                swap(arr, j, j - 1);
            }
//            CommonUtils.print(arr);
//            System.out.println();
        }

    }

    public static void main(String[] args) {
        Integer arr[] = CommonUtils.generateRandomArr(10000, 1, 10000);
        CommonUtils.testSort("com.yyq.sort.InsertSort",arr);

        arr = CommonUtils.generateRandomArr(10000, 1, 10000);
        CommonUtils.testSort("com.yyq.sort.SelectionSort",arr);
//        Integer[] arr = {4, 3, 6, 2, 1, 9, 5, 8, 7};
//        new InsertSort<Integer>().sort(arr);

    }
}
