# 排序
## [排序算法总结]

## [快速排序](DataStruct/src/main/java/com/yyq/sort/quickSort)
<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E6%8E%92%E5%BA%8F%E7%AE%97%E6%B3%95%E6%80%BB%E7%BB%93.png" width="400" "/> </div><br>  

### 1.双路快排
<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%8F%8C%E8%B7%AF%E5%BF%AB%E6%8E%92%E8%BF%87%E7%A8%8B.png" width="200" "/> </div><br>  

快速排序通过一个切分元素将数组分为两个子数组，左子数组小于等于切分元素，右子数组大于等于切分元素，将  这两个子数组排序也就将整个数组排序了。  

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%8F%8C%E8%B7%AF%E5%BF%AB%E6%8E%92.png" width="200"/> </div><br>  

```java
class QuickSort{
    /**
     * 双路快排
     */
    int partition2(Integer[] arr, int l, int r) {
        Integer v = arr[l];

        // arr[l+1...i] <= v ; arr[j...r] >= v
        int i = l + 1, j = r;
        while (true) {
            while (i <= r && arr[i] < v) i++;
            while (j >= l + 1 && arr[j] > v) j--;
            if (i > j) break;
            CommonUtils.swap(arr, i, j);
            i++;
            j--;
        }
        CommonUtils.swap(arr, l, j);
        return j;
    }

    void quickSort2(Comparable[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int p = partition2((Integer[]) arr, l, r);
        quickSort2(arr, l, p - 1);
        quickSort2(arr, p + 1, r);
    }

    void sort2(T[] arr) {
        quickSort2(arr, 0, arr.length - 1);
    }
}
```



## [堆排序](DataStruct/src/main/java/com/yyq/heap)

### 1.堆
二叉堆是一棵完全二叉树，满足如下两个性质：
(1)堆中某个节点的值不大于其父节点的值；  
(2)二叉堆总是一棵完全二叉树（大顶堆）。  
堆可以用数组来表示，这是因为堆是完全二叉树，而完全二叉树很容易就存储在数组中。位置 k 的节点的父节点   位置为 k/2，而它的两个子节点的位置分别为 2k 和 2k+1。这里不使用数组索引为 0 的位置，是为了更清晰地描述    节点的位置关系。   

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%A0%86%E5%AE%9A%E4%B9%89.jpg" width="400" length="400"/> </div><br>  

```java
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
    void push(T item) {
        data[count + 1] = item;
        shiftUp(count + 1);
        count++;
    }

    Object pop() {
        Object res = data[1];
        swap(data, 1, count);
        count--;
        shiftDown(1);
        return res;
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

```

### 2.ShiftUp

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/ShiftUp.jpg" width="400"/> </div><br>

```java
// 向堆中添加一个元素
    void push(T item){
        data[count+1] = item;
        shiftUp(count+1);
        count++;
    }

    /**
     * Shift Up
     */
    private void shiftUp(int k){
        while(k>1 && data[k].compareTo(data[k/2])>0){
            swap(data,k,k/2);
            k/=2;
        }
    }
```

### 3.ShiftDown

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/ShiftDown.jpg" width="400"/> </div><br>  

```java
// 弹出堆顶元素，弹出之后向下浮动
Object pop(){
        Object item = data[1];
        swap(data,1,count);
        count--;
        shiftDown(1);
        return item;
    }

    /**
     * Shift Down
     */
    private void shiftDown(int index){
        while( 2 * index <= count){
//            if(data[index*2].compareTo(data[index*2+1])>0){
//                swap(data,index,index*2);
//            }else{
//                swap(data,index,index*2+1);
//            }
            // 确定index的索引！经典代码！！
            int j = 2*index;
            if(j+1 <= count && data[j+1].compareTo(data[j])>0)
                j += 1;
            if(data[index].compareTo(data[j])>=0)
                break;
            swap(data,index,j);

            index = j;
        }
    }
```

### 4.堆排序(需要申请额外的空间)

```java
/**
 * 堆排序。时间复杂度为NlogN
 * @author yyq
 * @since 2018/10/10
 */
public class HeapSort {

    // sort1 遍历一次元素依次插入堆中，在从堆中循环取出元素即可
    public void sort1(Comparable[] arr) {
        int n = arr.length;
        MaxHeap<Integer> maxHeap = new MaxHeap<Integer>(n);
        for (int i = 0; i < n; i++) {
            maxHeap.push((Integer) arr[i]);
        }

        for (int j = 0; j < n; j++) {
            System.out.println(maxHeap.pop());
        }
    }

    // sort2 不需要申请额外的空间
    public void sort2(Comparable[] arr,int n){
        // 在构造函数中完成构建堆的过程
        MaxHeap<Integer> maxHeap = new MaxHeap<Integer>((Integer[]) arr,n);
        for (int j = 0; j < n; j++) {
            System.out.println(maxHeap.pop());
        }
    }

    public static void main(String[] args) {
        Integer[] arr = CommonUtils.generateRandomArr(10,1,50);
         new HeapSort().heapify(arr,10);
    }

}
```

### 5.优化堆排序(不需要申请额外空间)
<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/sort2.jpg" width="400" length="400"/> </div><br>  


```java
/**
 * 优化的堆排序，时间复杂度为NlogN，不需要开辟额外的空间，空间复杂度为O(1)
 *
 * @author yyq
 * @since 2018/10/11
 */
public class HeapSort2<T extends Comparable<T>> {
    public void sort(T[] arr, int n) {
        // heapify 构建堆
        for (int i = (n - 1) / 2; i >= 0; i--) {
            shiftDown(arr, n, i);
        }
        // 交换首尾元素，交换之后在进行heapify操作，每次循环--
        for (int i = n - 1; i > 0; i--) {
            CommonUtils.swap(arr, 0, i);
            shiftDown(arr, i, 0);
        }
    }

    // 对arr[]中索引位置位i的元素进行shiftDown操作
    private void shiftDown(T[] arr, int n, int index) {
        while (2 * index + 1 < n) {
            int k = 2 * index + 1;
            if ((k + 1 < n) && (arr[k].compareTo(arr[k + 1]) < 0))
                k = k + 1;
            if (arr[index].compareTo(arr[k]) >= 0)
                break;
            CommonUtils.swap(arr, k, index);
            index = k;
        }
    }

    public static void main(String[] args) {
        Integer[] arr = CommonUtils.generateRandomArr(10, 1, 50);
        new HeapSort2<Integer>().sort(arr, 10);
        CommonUtils.println(arr);
    }
}
```
