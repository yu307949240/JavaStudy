* [一.排序算法](#一排序)
  * [排序算法总结](#排序算法总结)
  * [1.快速排序](#1快速排序)
  * [2.堆排序](#2堆排序)
* [二.BST](#二bst)
  * [1.BST定义](#1bst定义)
  * [2.插入节点](#2插入)
  * [3.查找节点](#3查找)
  * [4.层次遍历(广度优先遍历)](#4层次遍历广度优先遍历)
  * [5.寻找最大值与最小值](#5寻找最大值与最小值)
  * [6.删除最大值节点或最小值节点](#6删除最大值节点或最小值节点)
  * [7.删除任意一个节点](#7删除任意一个节点)
* [三.其他二叉树](#三其他二叉树)
  * [1.字典树(Trie)](#1字典树trie)
  * [2.红黑树(RBtree)](#2红黑树rbtree)
     * [左旋](#23左旋)
     * [右旋](#24右旋)
# 一.排序

## 排序算法总结

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E6%8E%92%E5%BA%8F%E7%AE%97%E6%B3%95%E6%80%BB%E7%BB%93.png" width="400" "/> </div><br> 

## [1.快速排序](https://github.com/yu307949240/JavaStudy/blob/master/DataStruct/src/main/java/com/yyq/sort/quickSort.java) 

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



## [2.堆排序](https://github.com/yu307949240/JavaStudy/tree/master/DataStruct/src/main/java/com/yyq/heap)

### [1.堆](https://github.com/yu307949240/JavaStudy/blob/master/DataStruct/src/main/java/com/yyq/heap/MaxHeap.java)
二叉堆是一棵完全二叉树，满足如下两个性质：

* (1)堆中某个节点的值不大于其父节点的值；  

* (2)二叉堆总是一棵完全二叉树（大顶堆，所以可以用数组来表示）。

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

### [4.堆排序(需要申请额外的空间)](https://github.com/yu307949240/JavaStudy/blob/master/DataStruct/src/main/java/com/yyq/heap/HeapSort.java)

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

### [5.优化堆排序(不需要申请额外空间)](https://github.com/yu307949240/JavaStudy/blob/master/DataStruct/src/main/java/com/yyq/heap/HeapSort2.java)
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

# [二.BST](https://github.com/yu307949240/JavaStudy/blob/master/DataStruct/src/main/java/com/yyq/bst/BST.java)

BST树性质：

* (1)：某一个节点的值大于左子节点，并且小于右子节点；

* (2)：不是一个完全二叉树(所以不能用数组来表示) 


## 1.BST定义

```java
/**
 * 二分搜索树BST，删除BST中任意一个节点时间复杂度为O(logn)
 *
 * @author yyq
 * @since 2018/10/12
 */
public class BST<Key extends Comparable<Key>, Value> {
    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            left = right = null;
        }

        public Node(Node node) {
            this.key = node.key;
            this.value = node.value;
            this.left = node.left;
            this.right = node.right;
        }
    }

    private Node root;
    private int count;

    public BST() {
        root = null;
        count = 0;
    }

    int size() {
        return count;
    }

    boolean isEmpty() {
        return count == 0;
    }
}
```

## 2.插入

```java
void insert(Key key, Value value) {
        root = insert(root, key, value);
    }
// 插入以node为根的二叉搜索树中，插入节点(key, value), 返回新节点后的二叉搜索树的根
    private Node insert(Node node, Key key, Value value) {
        if (node == null) {
            count++;
            return new Node(key, value);
        }
        if (key.equals(node.key))
            node.value = value;
        else if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, value);
        } else {
            node.right = insert(node.right, key, value);
        }
        return node;
    }
```

## 3.查找

```java
Value search(Key key) {
        return search(root, key);
    }
private Value search(Node node, Key key) {
        if (node == null)
            return null;
        if (node.key == key) {
            return node.value;
        } else if (node.key.compareTo(key) < 0) {
            return search(node.left, key);
        } else {
            return search(node.right, key);
        }
    }
```

## 4.层次遍历(广度优先遍历)

```java
/**
     * 广度优先遍历(层次遍历)
     * 只能使用非递归方式进行遍历
     * 反证法证明：
     * 如果能实现对 A 节点的层序递归，在对 A 节点处理的过程中，应该递归的对两个儿子 B 和 C 分别调用了层序遍历。
     * 在这种情况下，我们无法让 B 和 C 的同一个层级的儿子在集中的时间中被遍历到，换言之，B 的第一层儿子在对 B
     * 的调用中被遍历，而 C 的第一层儿子，则在对 C 的调用中遍历，这是分离开的。不成立，得证。
     */ 
void levelOrder() {
        levelOrder(root);
    }
private void levelOrder(Node node) {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node n = queue.remove();
            System.out.println(n.value);
            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
        }
    }
```

## 5.寻找最大值与最小值

```java
// 寻找BST树中最小值
    Key minimum() {
        assert (count != 0);
        Node n = minimum(root);
        return n.key;
    }

    // 寻找BST树中最大值
    Key maximum() {
        assert (count != 0);
        Node n = maximum(root);
        return n.key;
    }

// 以node为根的BST中，返回最小键值的节点
    private Node minimum(Node node) {
//        if(node == null)
//            return null;
        if (node.left == null)
            return node;
        return minimum(node.left);
    }

    // 以node为根的BST中，返回最大键值的节点
    private Node maximum(Node node) {
        if (node.right == null)
            return node;
        return maximum(node.right);
    }
```

## 6.删除最大值节点或最小值节点
<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%88%A0%E9%99%A4BST%E4%B8%AD%E6%9C%80%E5%A4%A7%E5%80%BC.png" width="300" "/> </div><br>  

```java
void removeMin() {
        if (root != null)
            root = removeMin(root);
    }

void removeMax() {
        if (root != null) {
            root = removeMax(root);
        }
    }
// 删除掉以node为根的BST中的最小值节点
// 返回删除节点后新的BST的根
private Node removeMin(Node node) {
        if (node.left == null) {
            Node ritghNode = node.right; // 返回要删除节点的右子节点
//            if(ritghNode != null){
//                node = ritghNode;
//            }
            count--;
            return ritghNode;
        }
        node.left = removeMin(node.left); // 将要删除的节点的左子节点替换此节点！
        return node;
    }

private Node removeMax(Node node) {
        if (node.right == null) {
            Node leftNode = node.left;
            count--;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }
```

## 7.删除任意一个节点

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%88%A0%E9%99%A4BST%E4%B8%AD%E4%BB%BB%E6%84%8F%E8%8A%82%E7%82%B9.png" width="300" "/> </div><br>  

```java
    void remove(Key key) {
        root = remove(root, key);
    }
    // 删除掉以node为根，键值为key的节点
    // 返回删除节点之后BST树的根
    private Node remove(Node node, Key key) {
        if (node == null)
            return null;
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        } else { // key == node.key
            if (node.left == null) { // 删除节点没有左孩子或者左右孩子都为null
                Node rightNode = node.right;
                count--;
                return rightNode;
            }
            if (node.right == null) { // 删除节点没有右孩子
                Node leftNode = node.left;
                count--;
                return leftNode;
            }

            // node.left != null && node.right != null
            Node s = new Node(minimum(node.right));//minimum(node.right); // 当执行下边removeMin时，s的指向也就失败了，所以我们要重新的new一个node
            count++;
            s.right = removeMin(node.right); // 这里会执行count--
            s.left = node.left;
            count--;
            return s;
           /* Node s = new Node(maximum(node.left));
            count++;
            s.left = removeMax(node.left);
            s.right = node.right;
            count--;
            return s;*/
        }
    }
```

# 三.其他二叉树

## 1.字典树(Trie)

字典树属于不平衡二叉树，用于字符串的存储

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%AD%97%E5%85%B8%E6%A0%91%E5%AD%98%E5%82%A8%E5%AD%97%E7%AC%A6%E4%B8%B2.png" width="300" "/> </div><br>  

字典树的性质如下：

* 根节点没有字符路径。
* 从根节点到某一节点，将路径上经过的字符串连接起来，为扫过的对应字符串
* 每个节点向下所有字符路径上的字符都不同

字典树节点定义

```java
public class TrieNode{
    private int path;
    private int count;
    TrieNode[] map;
    public TrieNode(){
        path = 0;
        count = 0;
        map = new TrieNode[26];
    }
}
```

### 1.1 字典树实现

```java
/**
 * 字典树实现字符串的存储
 * @author yyq
 * @since 2018/10/13
 */
public class Trie {
    class TrieNode {
        int path; // 有多少个word经过
        int end; // 有多少个word以此节点结束
        TrieNode[] map;

        public TrieNode() {
            path = 0;
            end = 0;
            map = new TrieNode[26]; // 指向26个字母
        }
    }

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }
}
```

### 1.2 字典树插入字符串

```java
 // 向字典树中插入一个word
    public void insert(String word) {
        if (word == null) {
            return;
        }
        TrieNode node = root;
        int index = 0;
        char[] chs = word.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            index = chs[i] - 'a';
            if (node.map[index] == null) {
                node.map[index] = new TrieNode();
            }
            node = node.map[index];
            node.path++;
        }
        node.end++;
    }
```

### 1.3 判断字典树是否包含字符串

```java
// 查找字典树中是否包含word
    public boolean contain(String word) {
        if (word == null) {
            return false;
        }
        TrieNode node = root;
        int index = 0;
        char[] chs = word.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            index = chs[i] - 'a';
            if (node.map[index] == null) {
                return false;
            }
            node = node.map[index];
        }
        return node.end != 0;
    }
```

### 1.4 删除字典树中的字符串

```java
// 删除字典树中的word
    public void delete(String word){
        if(contain(word)) {
            TrieNode node = root;
            int index = 0;
            char[] chs = word.toCharArray();
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.map[index].path-- == 1) {
                    node.map[index] = null;
                }
                node = node.map[index];
            }
            node.end--;
        }
    }
```

## 2.红黑树(RBtree)

### 2.1特性

红黑树是一种特殊的BST，是一种**平衡二叉树**满足下述五个性质

* (1) 每个节点要么是红色要么是黑色；

* (2) 根节点是黑色；
* (3) 每个叶子节点，注意是空的叶子节点是黑色；
* (4) 如果一个叶子节点是红色，那么它的叶子节点必须是黑色；
* (5) 每一个节点到该节点的子孙节点的所有路径上包含相同数目的黑节点。

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E7%BA%A2%E9%BB%91%E6%A0%91.jpg" width="300" "/> </div><br>  


### 2.2红黑树数据结构定义

```java
/**
 * 红黑树
 *
 * @author yyq
 * @since 2018/10/21
 */
public class RBTree<T extends Comparable<T>> {

    class RBTNode<T extends Comparable<T>> {
        boolean color;
        T k;
        RBTNode<T> left, right, parent;

        public RBTNode(T k, boolean color, RBTNode<T> left, RBTNode<T> right, RBTNode<T> parent) {
            this.k = k;
            this.color = color;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    private RBTNode<T> root;
    private static final boolean RED = false;
    private static final boolean BLACk = true;
}

```



### 2.3左旋

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%B7%A6%E6%97%8B.jpg" width="300" "/> </div><br>  

```java
/**
     * 对红黑树的节点(x)进行左旋转
     * <p>
     * 左旋示意图(对节点x进行左旋)：
     *   px                              px
     *   /                               /
     *  x                               y
     * /  \      --(左旋)-              / \
     *lx   y                          x  ry
     * /   \                         /  \
     * ly   ry                      lx  ly
     */
    public void leftRotate(RBTNode<T> x) {
        RBTNode<T> y = x.right;
        x.right = y.left;
        y.parent = x.parent;
        if (x.parent == null) // 如果 “x的父亲” 是空节点，则将y设为根节点
            root = y;
        else {
            if (x.parent.left == x)
                x.parent.left = y; // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
            else {
                x.parent.right = y; //  如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
            }
        }
        y.left = x;
        x.parent = y;
    }
```



### 2.4右旋

<div align="center"> <img src="https://github.com/yu307949240/JavaStudy/blob/master/pics/%E5%8F%B3%E6%97%8B.jpg" width="300" "/> </div><br>  

```java
/**
 * 对红黑树的节点(y)进行右旋转
 * <p>
 * 右旋示意图(对节点y进行左旋)：
 *     py                               py
 *     /                                /
 *    y                                x
 *   /  \      --(右旋)-               /  \
 *  x   ry                           lx   y
 * / \                                   / \
 *lx  rx                               rx  ry
 */

public void rightRotate(RBTNode<T> y) {
    RBTNode<T> x = y.left;
    y.left = x.parent;
    y.parent = x.parent;
    if (x.parent == null)
        root = x;
    else {
        if (y == y.parent.right)
            y.parent.right = x;
        else {
            y.parent.left = x;
        }
    }
    x.right = y;
    y.parent = x;
}
```



