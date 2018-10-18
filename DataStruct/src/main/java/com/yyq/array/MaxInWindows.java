package com.yyq.array;

import com.yyq.heap.MaxHeap;

import java.util.ArrayList;

/**
 * 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。
 * 例如，如果输入数组 {2, 3, 4, 2, 6, 2, 5, 1} 及滑动窗口的大小 3，那么一共存在 6 个滑动窗口，他们的最大值分别为 {4, 4, 6, 6, 6, 5}。
 */
public class MaxInWindows {
    public ArrayList<Integer> maxInWindows(int[] num,int size){
        ArrayList<Integer> res = new ArrayList<Integer>();
        if(size>num.length||size<1){
            return res;
        }
        MaxHeap<Integer> mapHeap = new MaxHeap<Integer>(size);
        for(int i=0;i<size;i++){
            mapHeap.push(num[i]);
        }
        for(int i=0,j=i+size-1;j<num.length;i++,j++){
            // todo mapHeap.remove(num[i-1]);
            mapHeap.push(num[j]);
            res.add((Integer) mapHeap.extractMax());
        }
        return res;
    }
}
