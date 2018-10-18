package com.yyq.tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 二叉树
 *
 * @author yyq
 * @since 2018/10/16
 */
public class Tree {
    static class Node {
        public int value;
        Node left, right;

        public Node(int data) {
            value = data;
            left = right = null;
        }
    }

    /**
     * 根据二叉树的前序遍历和中序遍历的结果，重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
     * preorder = [3,9,20,15,7]
     * inorder =  [9,3,15,20,7]
     */

    private Map<Integer, Integer> inIndexMap = new HashMap<Integer, Integer>();

    public Node reConstructTree(int[] pre, int[] in) {
        for (int i = 0; i < in.length; i++)
            inIndexMap.put(in[i], i);
        return reConstructTree(pre, 0, pre.length - 1, 0);
    }

    public Node reConstructTree(int[] pre, int preL, int preR, int inL) {
        Node root = new Node(pre[preL]);
        int indexForInOrder = inIndexMap.get(root.value);
        int leftTreeSize = indexForInOrder - inL;
        root.left = reConstructTree(pre, preL + 1, preL + leftTreeSize, inL);
        root.right = reConstructTree(pre, preL + leftTreeSize + 1, preR, inL + leftTreeSize + 1);
        return root;
    }

    /**
     * 找出二叉树中下一个节点，给定一个二叉树节点，找到中序遍历的下一个节点
     * ① 如果一个节点的右子树不为空，那么该节点的下一个节点是右子树的最左节点；
     * ② 否则，向上找第一个左链接指向的树包含该节点的祖先节点。
     */
    class TreeLinkNode {
        int val;
        TreeLinkNode left, right, next;

        public TreeLinkNode(int data) {
            val = data;
            left = right = next = null;
        }
    }

    public TreeLinkNode getNextNode(TreeLinkNode n) {
        if (n == null)
            return null;

        if (n.right != null) {
            TreeLinkNode right = n.right;
            while (right.left != null) {
                right = right.left;
            }
            return right;
        } else {
            while (n.next != null) {
                TreeLinkNode parent = n.next;
                while (parent.left == n)
                    return parent;
                parent = parent.next;
            }
        }
        return null;
    }

    /**
     * 找到二叉树中和为某一值的路径
     */
    private ArrayList<ArrayList<Integer>> ret = new ArrayList<ArrayList<Integer>>();

    private ArrayList<ArrayList<Integer>> findPathSum(Node n, int target, ArrayList<Integer> path) {
        if (n == null)
            return null;
        path.add(n.value);
        target -= n.value;
        if (target == 0 && n.left == null && n.right == null) {
            ret.add(new ArrayList<Integer>(path));
        } else {
            findPathSum(n.left, target, path);
            findPathSum(n.right, target, path);
        }
        path.remove(path.size() - 1);
        return ret;
    }

    /**
     * 二叉树寻找一个特定的子节点,找出根节点到这个子节点路径
     */
    private ArrayList<ArrayList<Integer>> findPathNode(Node n, int toFind, ArrayList<Integer> path) {
        if (n == null)
            return null;
        path.add(n.value);
        if (n.value == toFind && n.left == null && n.right == null) {
            ret.add(new ArrayList<Integer>(path));
        } else {
            findPathNode(n.left, toFind, path);
            findPathNode(n.right, toFind, path);
        }
        path.remove(path.size() - 1);
        return ret;
    }

    /**
     * 输出二叉树根节点到叶子节点的所有路径
     */
    private ArrayList<ArrayList<Integer>> findAllPath(Node n, ArrayList<Integer> path) {
        if (n == null)
            return null;
        path.add(n.value);
        if (n.left == null && n.right == null)
            ret.add(new ArrayList<Integer>(path));
        else {
            findAllPath(n.left, path);
            findAllPath(n.right, path);
        }
        path.remove(path.size() - 1);
        return ret;
    }

    /**
     * 找到BST中两个节点的公共祖先节点
     * 满足n.val>=p.val && n.val<=q.val
     */
    public Node getCommonAncestor(Node n, Node p, Node q) {
        if (n == null)
            return n;
        if (n.value > p.value && n.value > q.value) {
            return getCommonAncestor(n.left, p, q);
        }
        if (n.value < p.value && n.value < q.value) {
            return getCommonAncestor(n.right, p, q);
        }
        return n;
    }

    /**
     * 找出普通二叉树中两个节点的公共祖先节点
     * 在左右子树中查找是否存在 p 或者 q，如果 p 和 q 分别在两个子树中，那么就说明根节点就是最低公共祖先。
     */
    public Node lowestCommonAncestor(Node n, Node p, Node q) {
        if (n == null || p == null || q == null)
            return n;
        Node left = lowestCommonAncestor(n.left, p, q);
        Node right = lowestCommonAncestor(n.right, p, q);
        return left == null ? right : right == null ? left : n;
    }

    /**
     * 二叉树深度
     */
    public int TreeDepth(Node root) {
        return root == null ? 0 : 1 + Math.max(TreeDepth(root.left), TreeDepth(root.right));
    }

    /**
     * 判断二叉树是否是平衡二叉树
     */
    public boolean isBalanced = true;

    public boolean isBalanced(Node root) {
        height(root);
        return isBalanced;
    }

    public int height(Node n) {
        if (n == null || !isBalanced)
            return 0;
        int left = height(n.left);
        int right = height(n.right);
        if (Math.abs(left - right) > 1)
            isBalanced = false;
        return 1 + Math.max(left, right);
    }

    public static void main(String[] args) {
        Node node = new Node(10);
        Node n1 = new Node(5);
        Node n2 = new Node(12);
        Node n3 = new Node(4);
        Node n4 = new Node(7);
        node.left = n1;
        node.right = n2;
        n1.left = n3;
        n1.right = n4;
//        ArrayList<ArrayList<Integer>> res = new Tree().findAllPath(node,  new ArrayList<Integer>());
//        for (ArrayList<Integer> list : res) {
//            for (Integer i : list) {
//                System.out.print(" " + i);
//            }
//            System.out.println();
//        }
        Node n = new Tree().getCommonAncestor(node, n1, n4);
        System.out.println(n.value);
    }
}
