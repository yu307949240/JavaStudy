package com.yyq.linkedlist;


/**
 * 单向链表实现
 *
 * @author yyq
 * @since 2018/10/14
 */
public class LinkedList<E> {
    private class Node {
        E e;
        private Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e, null);
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    private int size;
    private Node head;

    // 在索引位置为index加入元素
    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Illegal index");
        if (index == 0) {
            head = new Node(e);

        } else {
            Node n = head;
            for (int i = 0; i < index; i++) {
                n = n.next;
            }
            n.next = new Node(e, n.next);
        }
        size++;
    }

    // 在链表头部添加元素
    public void addFirst(E e) {
        add(0, e);
    }

    // 向链表中最后一个位置添加元素
    public void addLast(E e) {
        add(size, e);
    }

    // 获取索引位置为index元素
    public E get(int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("get failed. Illegal index");
        Node n = head;
        for (int i = 0; i < index; i++)
            n = n.next;
        return n.e;
    }

    // 修改链表中索引位置为index的元素
    public void set(int index, E e) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Set failed. Illegal index");
        Node n = head;
        for (int i = 0; i < index; i++) {
            n = n.next;
        }
        n.e = e;
    }

    // 删除索引位置为index元素
    // 返回删除的元素
    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed. Illegal index");
        Node n = head;
        for (int i = 0; i < index; i++)
            n = n.next;
        Node res = n.next;
        n.next = res.next;
        res.next = null;
        size--;

        return res.e;
    }

    // 从链表中删除第一个元素, 返回删除的元素
    public E removeFirst(){
        return remove(0);
    }

    public void removeElement(E e) {
        Node n = head;
        while (n.next != null) {
            if (n.next.e.equals(e))
                break;
            n = n.next;
        }
        if (n.next != null) {
            Node delNode = n.next;
            n.next = delNode.next;
            delNode.next = null;
            size--;
        }
    }

}
