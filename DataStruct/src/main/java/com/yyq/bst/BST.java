package com.yyq.bst;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二分搜索树BST
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


}
