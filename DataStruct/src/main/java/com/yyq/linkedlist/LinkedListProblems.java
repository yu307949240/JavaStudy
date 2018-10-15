package com.yyq.linkedlist;

import java.util.*;
/**
 * 链表
 *
 * @author yyq
 * @since 2018/10/14
 */
public class LinkedListProblems {
    class Node {
        public int value;
        public Node next;

        public Node(int d) {
            value = d;
        }
    }

    /**
     * 打印两个链表的公共部分
     */
    public void printCommonPart(Node h1, Node h2) {
        if (h1.value < h2.value)
            h1 = h1.next;
        else if (h1.value > h2.value)
            h2 = h2.next;
        else {
            System.out.println(h1.value);
            h1 = h1.next;
            h2 = h2.next;
        }
    }

    /**
     * 判断链表中是否含有环
     */
    public boolean hasLoop(Node head) {
        Node first = head;
        Node sec = head;
        while (sec.next != null) {
            first = first.next;
            sec = sec.next.next;
            if (first == sec)
                return true;
            return false;
        }
        return false;
    }

    /**
     * 返回链表倒数第个节点
     */
    public int getLastKth(Node node, int k) {
        Node first = node;
        Node sec = node;
        for (int i = 0; i < k; i++) {
            sec = sec.next;
        }
        while(sec.next!=null){
            first = first.next;
            sec = sec.next;
        }
        return first.value;
    }

    /**
     * 反转单向链表
     */
    public void reverse(Node head){
        Node pre = null;
        Node next = null;
        while(head!=null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
    }

    /**
     * 判断链表中存储的元素是否为回文结构
     * 1-2-1 true
     * 1-2-2-1 true
     */
    public boolean isPalindromel(Node head){
        java.util.Stack<Node> stack = new java.util.Stack<Node>();
        Node n = head;
        while(n!=null){
            stack.push(n);
            n = n.next;
        }
        while(head != null){
            if(head.value != stack.pop().value){
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 合并两个有序链表(递归)
     */
    public Node merge(Node h1,Node h2){
        if(h1 == null){
            return h2;
        }
        if(h2 == null){
            return h1;
        }
        if(h1.value < h2.value){
            h1.next = merge(h1.next,h2);
            return h1;
        }else{
            h2.next = merge(h1,h2.next);
            return h2;
        }
    }

}
