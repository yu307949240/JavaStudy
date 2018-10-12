package com.yyq.bst;

/**
 * 二分查找法
 * @author yyq
 * @since 2018/10/12
 */
public class BrinarySearch {
    int brinarySearch(int[] arr,int n,int target){
        int l = 0,r = n - 1;
        while(l<=r){
            // int m = (l + r) / 2; // 如果l和r都是max value 那么会产生溢出
            int m = l + (l+r)/2;
            if (arr[m] == target)
                return m;
            if(arr[m]>target)
                r = m - 1;
            if(arr[m]<target)
                l = m + 1;
        }
        return -1;
    }
}
