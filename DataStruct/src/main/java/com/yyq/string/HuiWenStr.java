package com.yyq.string;

/**
 * 回文字符串相关
 * @author yyq
 * @since 2018/10/17
 */
public class HuiWenStr {

    /**
     * 判断一个字符串是否为回文字符串
     */
    public boolean huiwenTest(String str){
        StringBuffer sb = new StringBuffer(str);
        StringBuffer reverseSb = sb.reverse();
        String newStr = new String(reverseSb);
        if(str.equals(newStr)){
            return true;
        }
        return false;
    }

    /**
     * 找出一个字符串中最长回文字符串
     */
    public String longestPalindrome(String s){
        if(s.length() == 1)
            return s;
        if(s.length() == 2 && s.charAt(0) == s.charAt(1))
            return s;
        boolean[][] isLongestPalindrome = new boolean[s.length()][s.length()];
        //最长回文串初始最大为0
        int maxlen = 0;
        //对应的maxlen的开始索引位置
        int beginIndex = 0;
        //对应的maxlen的结束索引位置
        int lastIndex = 0;
        for(int i=0;i<s.length();i++){
            int j = i;
            while(j>0){
                //满足上述的第三个条件，即当前s.charAt(i)==s.charAt(j)并
                //且s[j＋1到i－1]也是回文串
                if(s.charAt(i)==s.charAt(j)&&(i-j<2||isLongestPalindrome[j+1][i-1])){
                    isLongestPalindrome[j][i] = true;
                    if(maxlen<i-j+1){
                        beginIndex = j;
                        lastIndex = j+1;
                        maxlen = i-j+1;
                    }
                }
                j--;
            }
        }
        return s.substring(beginIndex,lastIndex);
    }
}
