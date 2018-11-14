package com.yyq.array;

import java.util.List;
import java.util.Vector;

/**
 * 一个长度为2N的数组，前面N个是数字，后面N个是字母，类似123abc,让转化为1a2b3c
 *
 * gap = 1
 */
public class Convert {
    void convert(Vector<String> ele, int gap){
        int len = ele.size();
        int n = len / 2;
        if(gap<n){
            for(int i = gap;i<n;){
                for(int j=0;j<gap;j++){
                    // swap(ele[i + j],ele[n + i + j - gap  ]);
                }
                // 偶数位保持不动
                i += 2*gap;
            }
            convert(ele,2*gap);
        }
    }


}
