package com.yyq.array;

/**
 * 定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。
 * 如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * 输入: [1,3,5,6], 5
 * 输出: 2
 */
public class SearchInsert {
    public int searchInsert(int[] arr,int tartget){
        for(int i=0;i<arr.length;i++){
            if(arr[i]>=tartget)
                return i;
        }
        return arr.length;
    }
}
