package com.yyq.num;

import java.util.PriorityQueue;

/**
 * 数据流中的中位数
 *
 * @author yyq
 * @since 2018/11/28
 */
public class Median {
    private PriorityQueue<Integer> max = new PriorityQueue<Integer>((o1, o2) -> o2 - o1);
    private PriorityQueue<Integer> min = new PriorityQueue<Integer>();

    private int cnt = 0;
    public void insert(Integer data){
        if(cnt%2 == 0){
            max.add(data);
            min.add(max.poll());
        }else{
            min.add(data);
            max.add(min.poll());
        }
        cnt++;
    }

    public Integer median(){
        if(cnt%2==0){
            return (max.peek()+min.peek())/2;
        }else{
            return min.peek();
        }
    }
}
