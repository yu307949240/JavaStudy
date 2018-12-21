package com.yyq.bloomfilter;

import sun.jvm.hotspot.utilities.Assert;

/**
 * 手动实现布隆过滤器
 *
 * @author yyq
 * @since 2018/12/14
 */
public class BloomFilters {
    private int arraySize;
    private int[] array;

    public BloomFilters(int arraySize) {
        this.arraySize = arraySize;
        array = new int[arraySize];
    }

    public void add(String str) {
        int first = hashcode_1(str);
        int second = hashcode_2(str);
        int third = hashcode_3(str);
        array[first % arraySize] = 1;
        array[second % arraySize] = 1;
        array[third % arraySize] = 1;
    }

    public boolean isContain(String str) {
        int first = hashcode_1(str);
        int second = hashcode_2(str);
        int third = hashcode_3(str);

        if (array[first % arraySize] == 0)
            return false;
        if (array[second % arraySize] == 0)
            return false;
        if (array[third % arraySize] == 0)
            return false;
        return true;
    }

    // hash算法1
    private int hashcode_1(String key) {
        int hash = 0;
        int i;
        for (i = 0; i < key.length(); i++) {
            hash = 33 * hash + key.charAt(i);
        }
        return Math.abs(hash);
    }

    // hash算法2
    private int hashcode_2(String key) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash ^ key.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return Math.abs(hash);
    }


    // hash算法3
    private int hashcode_3(String key) {
        int hash, i;
        for (hash = 0, i = 0; i < key.length(); ++i) {
            hash += key.charAt(i);
            hash += (hash << 10);
            hash ^= (hash >> 6);
        }
        hash += (hash << 3);
        hash ^= (hash >> 11);
        hash += (hash << 15);
        return Math.abs(hash);
    }

    public static void main(String[] args) {
        long star = System.currentTimeMillis();
        BloomFilters bloomFilters = new BloomFilters(10000000) ;
        for (int i = 0; i < 10000000; i++) {
            bloomFilters.add(i + "") ;
        }
        System.out.println(bloomFilters.isContain(1+""));
        System.out.println(bloomFilters.isContain(2+""));
        System.out.println(bloomFilters.isContain(3+""));
        System.out.println(bloomFilters.isContain(999999+""));
        System.out.println(bloomFilters.isContain(400230340+""));
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - star));
    }
}
