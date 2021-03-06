package com.yyq.heap;

import static com.yyq.sort.CommonUtils.swap;

/**
 * 用数组实现一个最大二叉堆。
 * 二叉堆满足以下两个性质:
 * 1.堆中某个节点的值总是不大于其父节点；
 * 2.堆总是一个完全二叉树。
 *
 * @author yyq
 * @since 2018/10/09
 */
public class MaxHeap<T extends Comparable<T>> {

    private T[] data;
    private int count;

    public MaxHeap(int capacity) {
        data = (T[]) new Comparable[capacity + 1];
        count = 0;
    }

    public MaxHeap(T[] arr, int n) {
        data = (T[]) new Comparable[n + 1];
        for (int i = 0; i < n; i++) {
            data[i + 1] = arr[i];
        }
        count = n;
        for (int i = count / 2; i >= 1; i--) {
            shiftDown(i);
        }
    }

    int size() {
        return count;
    }

    boolean isEmpty() {
        return count == 0;
    }

    // 向堆中添加一个元素
    public void push(T item) {
        data[count + 1] = item;
        count++;
        shiftUp(count);
    }

    public Object extractMax() {
        Object res = data[1];
        swap(data, 1, count);
        count--;
        shiftDown(1);
        return res;
    }

    public Object peek(){
        return data[1];
    }

    // 最大堆核心辅助函数

    /**
     * Shift Up
     */
    private void shiftUp(int k) {
        while (k > 1 && data[k].compareTo(data[k / 2]) > 0) {
            swap(data, k, k / 2);
            k /= 2;
        }
    }

    /**
     * Shift Down
     */
    private void shiftDown(int index) {
        while (2 * index <= count) {
            // 确定index的索引！经典代码！！
            int j = 2 * index;
            if (j + 1 <= count && data[j + 1].compareTo(data[j]) > 0)
                j += 1;
            if (data[index].compareTo(data[j]) >= 0)
                break;
            swap(data, index, j);

            index = j;
        }
    }

}
