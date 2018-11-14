package com.yyq.array;

/**
 * 判断一个数出现在排序数组中的次数
 *
 * @author yyq
 * @since 2018/11/14
 */
public class OccFreq {

    /**
     * 求解 k 在 arr 中第一次出现的索引位置
     * @param arr 有序数组
     * @param k
     * @return k第一次出现的索引位置,若 k 不在 arr中返回 -1
     */
    public static int getFirst(int[] arr, int k){
        if(arr == null || arr.length == 0)
            throw new IllegalArgumentException("arr == null or arr.lenght == 0");

        return getFirst(arr, 0, arr.length - 1, k);
    }

    private static int getFirst(int[] arr, int low, int high, int k){
        int middle = (low + high) / 2;
        if(middle == low || middle == high)
        {
            if(arr[middle] == k)
                return middle;
            else
//                throw new IllegalArgumentException(k + " not in arr");
                return -1;
        }

        if(arr[middle] > k)
            return getFirst(arr, low, middle - 1, k);
        else if(arr[middle] < k)
            return getFirst(arr, middle + 1, high, k);
        else
        {
            if(arr[middle - 1] == k)
                return getFirst(arr, low, middle - 1, k);
            else
                return middle;
        }
    }

    /**
     * 求解 k 在 arr 中最后一次出现的索引
     * @param arr
     * @param k
     * @return k 在arr中的最后出现的索引, 若 k 不在 arr中返回 -1
     */
    public static int getLast(int[] arr, int k){
        if(arr == null || arr.length == 0)
            throw new IllegalArgumentException("arr == null");
        return getLast(arr, 0, arr.length - 1, k);
    }
    private static int getLast(int[] arr, int low, int high, int k){
        int middle = (low + high) / 2;
        //已经寻找到最左边 或 最右边了.
        if(middle == low || middle == high)
        {
            if(arr[middle] == k)
                return middle;
            else
//                throw new IllegalArgumentException(k + " not in arr");
                return -1;//k 不在 arr中
        }
        if(arr[middle] > k){//继续在左边寻找
            return getLast(arr, low, middle - 1, k);
        }
        else if(arr[middle] < k){//继续在右边寻找
            return getLast(arr, middle + 1, high, k);
        }
        else//k== arr[middle]
        {
            if(arr[middle + 1] == k)
                return getLast(arr, middle + 1, high, k);//继续在右边寻找
            else
                return middle;
        }
    }

    /**
     * 求解 k 在 arr数组中出现的次数
     * @param arr 有序数组
     * @param k
     * @return k 在 arr 数组中出现的次数
     */
    public static int freq(int[] arr, int k){
        int first_index = getFirst(arr, k);
        int last_index = getLast(arr, k);
        if(first_index < 0 && last_index < 0)
            return 0;
        return last_index - first_index + 1;
    }

    public static void main(String[] args) {
        int[] arr = {2,4,5,6,8,8,8,9};

        int freqs = freq(arr, 8);
        System.out.println(freqs);
    }
}
