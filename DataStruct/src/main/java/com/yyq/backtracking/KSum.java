package com.yyq.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * KSum问题
 *
 * @author yyq
 * @since 2018/11/28
 */
public class KSum {
    public static List<List<Integer>> ret = new ArrayList<List<Integer>>();

    public List<List<Integer>> find(int[] arr, int target) {
        Arrays.sort(arr);
        backtracking(arr, target, 0, new ArrayList<Integer>());
        return ret;
    }

    // 每个数字只能用一次
    private static void backtracking(int[] arr, int target, int start, List<Integer> path) {
        if (target < 0) {
            return;
        } else if (target == 0) {
            ret.add(new ArrayList<Integer>(path));
        } else {
            for (int i = start; i < arr.length; i++) {
                path.add(arr[i]);
                backtracking(arr, target - arr[i], i + 1, path);
                path.remove(path.size() - 1);
            }
        }
    }

    public static void main(String[] args) {
     int arr[] = {6, 3, 2, 7, 15, 1, 2};
     backtracking(arr,10,0,new ArrayList<>());
     for(List l : ret){
         System.out.println(l.toString());
     }
    }

}
