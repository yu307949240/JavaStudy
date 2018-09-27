package com.yyq.sort;

import java.lang.reflect.Method;

/**
 * 排序算法辅助类
 *
 * @author yyq
 * @since 18/09/24
 */
public class CommonUtils {

    public static void swap(Object[] arr, int m, int n) {
        Object temp = arr[m];
        arr[m] = arr[n];
        arr[n] = temp;
    }

    public static void print(Object[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
    }

    public static void println(Object arr[]) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    /**
     * 生成元素个数为n的数组，数组元素值范围为[rangeL,rangeR]
     *
     * @param n
     * @param rangeL
     * @param rangeR
     * @return
     */
    public static Integer[] generateRandomArr(int n, int rangeL, int rangeR) {
        Integer[] resArr = new Integer[n];
        for (int i = 0; i < n; i++) {
            resArr[i] = (int) (Math.random() * (rangeR - rangeL + 1) + rangeL);
        }
        return resArr;
    }

    public static void testSort(String sortClassName, Comparable[] arr) {
        try {
            Class sortClass = Class.forName(sortClassName);
            Method sortMethod = sortClass.getMethod("sort", new Class[]{Comparable[].class});
            Object[] params = new Object[]{arr};
            long startTime = System.currentTimeMillis();
            sortMethod.invoke(sortClass.newInstance(), params);
            long endTime = System.currentTimeMillis();

            System.out.println(sortClass.getSimpleName() + ": " + (endTime - startTime) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Integer arr[] = generateRandomArr(5, 1, 8);
        print(arr);
        // CommonUtils.testSort();
    }
}
