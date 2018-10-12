package com.yyq.heap;

import static com.yyq.sort.CommonUtils.swap;

/**
 * 索引最大堆，用索引的形式来存储堆，原有堆中数据内容不变
 *
 * @author yyq
 * @since 2018/10/11
 */
public class IndexMapHeap<T extends Comparable<T>> {

    private T[] data;
    private Integer[] indexes;
    private int count;

    public IndexMapHeap(int capacity) {
        data = (T[]) new Comparable[capacity + 1];
        indexes = new Integer[capacity + 1];
        count = 0;
    }

    public IndexMapHeap(T[] arr, int n) {
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

    // 取出索引为i的值，从0开始
    T getItem(int i) {
        return data[i + 1];
    }

    // 修改data数组中索引为i的元素
    void change(int i, T newItem) {
        i += 1;
        data[i] = newItem;
        // 把i索引保存的位置尝试进行ShiftDown或者ShiftUp操作，找到indexes[j] = i,j表示data[i]在堆中的位置
        // 之后在ShiftUp(j),在ShiftDown(j)操作
        for (int j = 1; j <= count; j++){
            if(indexes[j]==i){
                shiftUp(j);
                shiftDown(j);
            }
        }
    }

    // 向堆中添加一个元素，外部用户index是从0开始的
    void push(int index, T item) {
        index += 1;
        data[index] = item;
        indexes[count + 1] = index;
        count++;
        shiftUp(count);
    }

    // 返回堆顶元素
    T extractMax() {
        T res = data[indexes[1]];
        swap(data, indexes[1], indexes[count]);
        count--;
        shiftDown(1);
        return res;
    }

    // 返回堆顶元素的索引
    Integer extractMaxIndex() {
        Integer res = indexes[1] - 1; // 外部索引是从0开始的
        swap(data, indexes[1], indexes[count]);
        count--;
        shiftDown(1);
        return res;
    }

    // 最大堆核心辅助函数

    /**
     * Shift Up
     */
    private void shiftUp(int k) {
        while (k > 1 && data[indexes[k]].compareTo(data[indexes[k / 2]]) > 0) {
            swap(indexes, indexes[k], indexes[k / 2]);
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
            if (j + 1 <= count && data[indexes[j + 1]].compareTo(data[indexes[j]]) > 0)
                j += 1;
            if (data[indexes[index]].compareTo(data[indexes[j]]) >= 0)
                break;
            swap(data, indexes[index], indexes[j]);

            index = j;
        }
    }

}

