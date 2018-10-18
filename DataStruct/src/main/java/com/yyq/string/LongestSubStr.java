package com.yyq.string;

import java.util.Arrays;

/**
 * 最长不含重复字符的子字符串
 *
 * @author yyq
 * @since 2018/10/16
 */
public class LongestSubStr {
    public int longestSubStr(String str) {
        int curLen = 0;
        int maxLen = 0;
        int[] preIndex = new int[26];
        Arrays.fill(preIndex, -1);

        for (int i = 0; i < str.length(); i++) {
            int c = str.charAt(i) - 'a';
            int preI = preIndex[c];
            if (preI == -1 || (i - preI > curLen)) {
                curLen++;
            } else {
                maxLen = Math.max(maxLen, curLen);
                curLen = i - preI;
            }
            preIndex[i] = i;
        }
        maxLen = Math.max(maxLen, curLen);
        return maxLen;
    }
}
