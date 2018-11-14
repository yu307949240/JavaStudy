package com.yyq.num;

import java.util.ArrayList;
import java.util.List;

/**
 * kSum问题
 *
 * @author yyq
 * @since 2018/11/07
 */
public class kSum {
    List<List<Integer>> ret = new ArrayList<List<Integer>>();

    public List<List<Integer>> kSum(int[] data, int k, int m) {
        backtracking(data, k, m, 0, new ArrayList<Integer>());
        return ret;
    }

    void backtracking(int[] data, int k, int m, int start, ArrayList<Integer> path) {
        if (m < 0) {
            return;
        } else if (m == 0) {
            if (path.size() == k)
                ret.add(new ArrayList<Integer>(path));
        } else {
            for (int i = start; i < data.length; i++) {
                path.add(data[i]);
                backtracking(data, k, m - data[i], i + 1, path);
                path.remove(path.remove(path.size() - 1));
            }
        }
    }
}
