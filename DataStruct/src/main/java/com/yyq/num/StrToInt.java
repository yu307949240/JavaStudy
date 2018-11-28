package com.yyq.num;

/**
 * 字符串转换成整数
 *
 * @author yyq
 * @since 2018/11/16
 */
public class StrToInt {
    public static int StrToInt(String str){
        if(str == null || str.length() == 0)
            return 0;
        boolean isNegative = str.charAt(0) == '-';
        int ret = 0;
        char[] chs = str.toCharArray();
        for(int i=0;i<chs.length;i++){
            int c = chs[i] - '0';
            if(i==0 && (chs[i] == '+' || chs[i] == '-'))
                continue;
            if(chs[i] < '0' || chs[i] > '9')
                return 0;
            ret = ret*10 + c;
        }
        return isNegative ? -ret : ret;
    }

    public static void main(String[] args) {
        System.out.println(StrToInt("-0012345"));
    }

}
