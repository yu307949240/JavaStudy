package com.yyq.array;

import java.util.Vector;

/**
 * 一个长度为2N的数组，前面N个是数字，后面N个是字母，类似123abc,让转化为1a2b3c
 * <p>
 * gap = 1
 */
public class Convert {
    public static void convert(String[] str, int gap) {
        int len = str.length;
        int n = len / 2;
        if (gap < n) {
            for (int i = gap; i < n; ) {
                for (int j = 0; j < gap; j++) {
                    swap(str, i + j, n + i + j - gap);
                }
                // 偶数位保持不动
                i += 2 * gap;
            }
            convert(str, 2 * gap);
        }
    }

    private static void swap(String[] str, int i, int j) {
        String tmp = str[i];
        str[i] = str[j];
        str[j] = tmp;
    }


    public static void main(String[] args) {
        String str[] = {"1", "2", "3", "a", "b", "c"};
        convert(str, 1);
        for (String s : str) {
            System.out.println(s);
        }
    }

}
