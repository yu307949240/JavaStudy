package com.yyq.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 字符串全排列
 *
 * @author yyq
 * @since 2018/11/28
 */
public class StrPermit {
    static List<String> ret = new ArrayList<String>();

    public static void permit(String str) {
        boolean[] marked = new boolean[str.length()];
        char[] chs = str.toCharArray();
        Arrays.sort(chs); // 可加可不加
        backtracking(chs, marked, new StringBuffer());
    }

    private static void backtracking(char[] chs, boolean[] marked, StringBuffer s) {
        if (s.length() == chs.length) {
            ret.add(s.toString());
            return;
        }
        for (int i = 0; i < chs.length; i++) {
            if (marked[i])
                continue;
            if (i != 0 && chs[i] == chs[i - 1] && !marked[i - 1]) /* 保证不重复 */
                continue;
             marked[i] = true;
            s.append(chs[i]);
    backtracking(chs, marked, s);
            s.deleteCharAt(s.length() - 1);
    marked[i] = false;
}
    }

    public static void main(String[] args) {
        permit("cba");
        for (String s : ret) {
            System.out.println(s);
        }
    }
}
