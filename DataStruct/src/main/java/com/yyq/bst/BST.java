package com.yyq.bst;

import java.util.LinkedList;
import java.util.Queue;

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

    void insert(Key key, Value value) {
        root = insert(root, key, value);
    }

    Value search(Key key) {
        return search(root, key);
    }

    boolean contain(Key key) {
        return contain(root, key);
    }

    /**
     * 深度优先遍历(前序，中序，后序遍历)
     * 递归遍历
     */
    void preOrder() {
        preOrder(root);
    }

    void inOrder() {
        inOrder(root);
    }

    void postOrder() {
        postOrder(root);
    }

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

    void removeMin() {
        if (root != null)
            root = removeMin(root);
    }

    void removeMax() {
        if (root != null) {
            root = removeMax(root);
        }
    }

    void remove(Key key) {
        root = remove(root, key);
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

    private boolean contain(Node node, Key key) {
        if (node == null) {
            return false;
        }
        if (node.key == key)
            return true;
        else if (node.key.compareTo(key) < 0) {
            return contain(node.left, key);
        } else {
            return contain(node.right, key);
        }
    }

    private void preOrder(Node node) {
        if (node != null) {
            System.out.println(node.value);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.value);
            inOrder(node.right);
        }
    }

    private void postOrder(Node node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.value);
        }
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


}
