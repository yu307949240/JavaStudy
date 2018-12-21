package com.yyq.num;

/**
 * 进制转换问题
 *
 * @author yyq
 * @since 2018/12/18
 */
public class ConvertNum {
    public static String convertToBase7(int num) {
        if (num == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        boolean isNegative = num < 0;
        if (isNegative) {
            num = -num;
        }
        while (num > 0) {
            sb.append(num % 8);
            num /= 8;
        }
        String ret = sb.reverse().toString();
        return isNegative ? "-" + ret : ret;
    }

    public static void main(String[] args) {
        System.out.println(convertToBase7(9));
    }
}
