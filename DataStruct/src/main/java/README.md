# 排序
## [快速排序](DataStruct/src/main/java/com/yyq/sort/quickSort)
## [堆排序](DataStruct/src/main/java/com/yyq/heap)
### 1.堆
二叉堆是一棵完全二叉树，满足如下两个性质：
(1)堆中某个节点的值不大于其父节点的值；  
(2)二叉堆总是一棵完全二叉树（大顶堆）。  
堆可以用数组来表示，这是因为堆是完全二叉树，而完全二叉树很容易就存储在数组中。位置 k 的节点的父节点位置为 k/2，而它的两个子节点的位置分别为 2k 和 2k+1。这里不使用数组索引为 0 的位置，是为了更清晰地描述节点的位置关系。

<div align="center"> <img src="../pics//f3080f83-6239-459b-8e9c-03b6641f7815.png" width="200"/> </div><br>  

```java
public class MaxHeap<T extends Comparable<T>> {

    private T[] data;
    private int count;

    public MaxHeap(int capacity){
        data = (T[]) new Comparable[capacity+1];
        count = 0;
    }

    int size(){
        return count;
    }

    boolean isEmpty(){
        return count == 0;
    }
}
```

### 2.ShiftUp

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
```java
// 弹出堆顶元素，弹出之后向下浮动
Object pop(){
        Object item = data[1];
        swap(data,1,count-1);
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

