package com.yyq.string;

/**
 * 反转单词顺序列
 *
 * @author yyq
 * @since 2018/10/16
 */
public class  ReverseStr {
    /**
     * Input:
     * "I am a student."
     * <p>
     * Output:
     * "student. a am I"
     * 正确的解法应该是先旋转每个单词，再旋转整个字符串。
     */
    public String reverseStr(String str) {
        int n = str.length();
        char[] chs = str.toCharArray();
        int i = 0, j = 0;
        while (j <= n) {
            if (j == n || chs[j] == ' ') {
                reverse(chs, i, j - 1);
                i = j + 1;
            }
            j++;
        }
        reverse(chs, 0, n - 1);
        return new String(chs);
    }

    /**
     * 左旋转字符串中前n个字符
     * Input:
     * S="abcXYZdef"
     * K=3
     *
     * Output:
     * "XYZdefabc"
     * 先反转0～n-1
     * 在反转n～str.len-1
     * 最后反转0～str.len-1
     */
    public String reverseStrKth(String str, int n) {
        if (n >= str.length()){
            return str;
        }
        char[] chs = str.toCharArray();
        reverse(chs,0,n-1);
        reverse(chs,n,str.length()-1);
        reverse(chs,0,str.length()-1);
        return new String(chs);
    }

    public void reverse(char[] chs, int i, int j) {
        while (i < j) {
            swap(chs, i, j);
        }
    }

    private void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }
}
