package com.yyq.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public int longestSubStr2(String str) {
        if (str.length() == 0)
            return 0;
        int maxLen = 0;
        List<Character> list = new ArrayList<>();
        list.add(str.charAt(0));
        for (int i = 1; i < str.length(); i++) {
            if (list.contains(str.charAt(i))) {
                list.subList(list.indexOf(str.charAt(i)) + 1, list.size());
                list.add(str.charAt(i));
                maxLen = Math.max(maxLen, list.size());
            } else {
                list.add(str.charAt(i));
                maxLen = Math.max(maxLen, list.size());
            }
        }
        return maxLen;
    }
}
