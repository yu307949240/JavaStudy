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
     * 两个链表相加
     * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
     * Output: 7 -> 0 -> 8
     */
    public Node addTwoNumbers(Node h1, Node h2) {
        Node h = new Node(0);
        if (h1 == null && h2 == null)
            return h;
        int sum = 0, carry = 0;
        Node cur = h;
        while (h1 != null || h2 != null) {
            int num1 = h1 == null ? 0 : h1.value;
            int num2 = h2 == null ? 0 : h2.value;
            sum = num1 + num2 + carry;
            cur.next = new Node(sum % 10);
            cur = cur.next;
            carry = sum / 10;
            h1 = h1 == null ? null : h1.next;
            h2 = h2 == null ? null : h2.next;
        }
        if (carry != 0)
            cur.next = new Node(carry);
        return h.next;
    }

    /**
     * 打印两个链表的公共部分
     */
    public void printCommonPart(Node h1, Node h2) {
        if (h1 == null || h2 == null)
            return;
        while (h1 != null && h2 != null) {
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
        while (sec.next != null) {
            first = first.next;
            sec = sec.next;
        }
        return first.value;
    }

    /**
     * 反转单向链表
     */
    public void reverse(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {
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
    public boolean isPalindromel(Node head) {
        java.util.Stack<Node> stack = new java.util.Stack<Node>();
        Node n = head;
        while (n != null) {
            stack.push(n);
            n = n.next;
        }
        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 合并两个有序链表(递归)
     */
    public Node merge(Node h1, Node h2) {
        if (h1 == null) {
            return h2;
        }
        if (h2 == null) {
            return h1;
        }
        if (h1.value < h2.value) {
            h1.next = merge(h1.next, h2);
            return h1;
        } else {
            h2.next = merge(h1, h2.next);
            return h2;
        }
    }

    /**
     * 合并k个有序链表
     */
    public Node mergeKLists(Node[] lists) {
        if (lists == null || lists.length == 0)
            return null;
        if(lists.length == 1)
            return lists[0];
        else if(lists.length == 2)
            return merge(lists[0],lists[1]);
        else{
            int half = (lists.length & 1) == 0 ? lists.length / 2
                    : lists.length / 2 + 1;
            Node[] merged = new Node[half];
            for(int i=0;i<half;i++){
                if(lists.length - 1 - i != i){
                    merged[i] = merge(lists[i],lists[lists.length-1-i]);
                }else{
                    merged[i] = lists[i];
                 }
            }
            return mergeKLists(merged);
        }

    }

    /**
     * 合并两个有序链表(迭代)
     */
    public Node merge1(Node h1, Node h2) {
        Node cur = new Node(-1);
        while (h1 != null && h2 != null) {
            if (h1.value <= h2.value) {
                cur.next = h1;
                h1 = h1.next;
            } else {
                cur.next = h2;
                h2 = h2.next;
            }
            cur = cur.next;
        }
        if (h1.next != null) {
            cur.next = h1;
        }
        if (h2.next != null) {
            cur.next = h2;
        }
        return cur.next;
    }

    /**
     * 找出链表中环的入口节点
     * 解题思路：使用双指针，一个指针 fast 每次移动两个节点，一个指针 slow 每次移动一个节点。因为存在环，
     * 所以两个指针必定相遇在环中的某个节点上。此时 fast 移动的节点数为 x+2y+z，
     * slow 为 x+y，由于 fast 速度比 slow 快一倍，因此 x+2y+z=2(x+y)，得到 x=z。
     */
    public Node EntryNodeOfLoop(Node h) {
        Node fast = h;
        Node slow = h;
        while (slow == fast) {
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = h;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 找出两个链表第一个公共子节点
     */
    public Node firstCommon(Node h1, Node h2) {
        int n = 0;
        Node cur1 = h1;
        Node cur2 = h2;
        while (cur1 != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2 != null) {
            n--;
            cur2 = cur2.next;
        }
        cur1 = (n > 0) ? h1 : h2;
        cur2 = cur1 == h1 ? h2 : h1;
        n = Math.abs(n);
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /**
     * 删除无序链表中的重复元素。利用set进行过滤
     */
    public void removeRepl(Node h) {
        Set<Node> set = new HashSet<Node>();
        Node pre = h;
        Node cur = h.next;
        set.add(h);
        while (cur != null) {
            if (set.contains(cur)) {
                pre.next = cur.next;
            } else {
                set.add(cur);
                pre = cur;
            }
            cur = cur.next;
        }
    }

    /**
     * 删除链表中值为某一个节点值的节点
     */
    public Node removeValue(Node h, int num) {
        Node pre = h;
        Node cur = h;
        if (cur != null) {
            if (cur.value == num) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return h;
    }

    /**
     * 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），返回结果为复制后复杂链表的 head。
     */
    class RandomNode {
        int label;
        RandomNode next = null;
        RandomNode random = null;

        RandomNode(int label) {
            this.label = label;
        }
    }

    public RandomNode clone(RandomNode n) {
        if (n == null)
            return null;
        // 插入新节点
        RandomNode cur = n;
        while (cur != null) {
            RandomNode clone = new RandomNode(cur.label);
            clone.next = cur.next;
            cur.next = clone;
            clone = clone.next;
        }

        // 建立random连接
        cur = n;
        while (cur != null) {
            RandomNode clone = cur.next;
            if (cur.random != null)
                clone.random = cur.random.next;
            cur = clone.next;
        }

        // 拆分
        cur = n;
        RandomNode pCloneHead = n.next;
        while (cur.next != null) {
            RandomNode next = cur.next;
            cur.next = next.next;
            cur = next;
        }
        return pCloneHead;
    }


}
