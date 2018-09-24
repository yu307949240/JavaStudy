package com.yyq.sort;

/**
 * 冒泡排序，On2,从左到右不断交换相邻逆序的元素，在一轮的循环之后，可以让未排序的最大元素上浮到右侧。
 * 在一轮循环中，如果没有发生交换，就说明数组已经是有序的，此时可以直接退出。
 * @author yyq
 * @since 18/09/24
 */
public class BubbleSort<T extends Comparable<T>> extends Sort<T> {

    void sort(T[] arr) {
        int n = arr.length;
        for(int i=n-1;i>0;i--){
            for(int j = 0;j<i;j++){
                if(less(arr[j+1],arr[j])){
                    swap(arr,j,j+1);
                }
            }
        }
    }

    public static void main(String[] args) {
        Integer[] arr = CommonUtils.generateRandomArr(10,1,50);
        new BubbleSort<Integer>().sort(arr);
        CommonUtils.print(arr);
    }
}
