package com.yyq.string;

/**
 * 替换空格
 * Input: "We Are Happy"
 * Output: "We%20Are%20Happy"
 *
 * @author yyq
 * @link https://github.com/CyC2018/CS-Notes/blob/master/notes/%E5%89%91%E6%8C%87%20offer%20%E9%A2%98%E8%A7%A3.md#5-%E6%9B%BF%E6%8D%A2%E7%A9%BA%E6%A0%BC
 * @since 2018/10/13
 */
public class ReplacePlcae {
        public static void replcaePlace(StringBuffer str) {
            int p1 = str.length() - 1;
            for (int i = 0; i <= p1; i++) {
                if (str.charAt(i) == ' ') {
                    str.append("  ");
                }
            }
            int p2 = str.length() - 1;
            while (p1 >= 0 && p2 > p1) {
                char c = str.charAt(p1--);
                if (c == ' ') {
                    str.setCharAt(p2--, '%');
                    str.setCharAt(p2--, '2');
                    str.setCharAt(p2--, '0');
                } else {
                    str.setCharAt(p2--, c);
                }
            }
        }

    public static void main(String[] args) {
        StringBuffer str = new StringBuffer("we are happy");
        replcaePlace(str);
        System.out.println(str);
    }
}
