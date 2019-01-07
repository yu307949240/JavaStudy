package com.yyq.array;

/**
 * 数组原地去重
 *
 * @author yyq
 * @since 2018/10/21
 */
public class RemoveDuplicates {
    public int removeDuplicates(int[] arr) {
        if (arr.length == 0)
            return 0;
        int pos = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[pos]) {
                pos++;
                arr[pos] = arr[i];
            }
        }
        return pos + 1;
    }
}
