package com.yyq.array;

/**
 * 判断数组中出现次数超过一半的数字
 *
 * @author yyq
 * @since 2018/10/24
 */
public class MoreHalfNum {
    public int moreHalfNum(int[] a) {
        int major = a[0];
        for (int i = 1, cnt = 1; i < a.length; i++) {
            cnt = a[i] == major ? cnt + 1 : cnt - 1;
            if (cnt == 0) {
                major = a[i];
                cnt = 1;
            }
        }
        int cnt = 0;
        for (int i : a) {
            if (a[i] == major)
                cnt++;
        }
        return cnt > a.length / 2 ? major : 0;
    }
}
