package com.yyq.tree;

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
    private static final boolean BLACK = true;

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

    public void insert(RBTNode<T> node) {
        int cmp;
        RBTNode<T> y = null;
        RBTNode<T> x = this.root;
        while (x != null) {
            y = x;
            cmp = node.k.compareTo(x.k);
            if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }
        node.parent = y;
        if (y != null) {
            cmp = node.k.compareTo(y.k);
            if (cmp < 0)
                y.left = node;
            else
                y.right = node;
        } else {
            this.root = node;
        }
        node.color = RED;
        insertFixUp(node);
    }

    /**
     * 红黑树插入修正函数
     * <p>
     * 在向红黑树中插入节点之后(失去平衡)，再调用该函数；
     * 目的是将它重新塑造成一颗红黑树。
     * <p>
     * 参数说明：
     * node 插入的结点        // 对应《算法导论》中的z
     */
    private void insertFixUp(RBTNode<T> node) {
        RBTNode<T> parent, gparent;
        while (((parent = parentOf(node)) != null) && isRed(parent)) {
            gparent = parentOf(parent);

            if (parent == gparent.left) {
                // Case 1条件：叔叔节点是红色
                RBTNode<T> uncle = gparent.right;
                if ((uncle != null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }
                // Case 2条件：叔叔是黑色，且当前节点是右孩子
                if (parent.right == node) {
                    RBTNode<T> tmp;
                    leftRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }
                // Case 3条件：叔叔是黑色，且当前节点是左孩子。
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else {
                // Case 1条件：叔叔节点是红色
                RBTNode<T> uncle = gparent.left;
                if ((uncle != null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是左孩子
                if (parent.left == node) {
                    RBTNode<T> tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是右孩子。
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }
        // 将根节点设为黑色
        setBlack(this.root);
    }

    public void remove(RBTNode<T> node) {
        RBTNode<T> child, parent;
        boolean color;
        if ((node.left != null) && (node.right != null)) {
            RBTNode<T> replace = node;
            replace = node.right;
            while (replace.left != null)
                replace = replace.left;
            if (parentOf(node) != null) {
                if (parentOf(node).left == node)
                    parentOf(node).left = replace;
                else {
                    parentOf(node).right = replace;
                }
            } else {
                this.root = replace;
            }
            child = replace.right;
            parent = parentOf(replace);
            color = colorOf(replace);
            if (parent == node) {
                parent = replace;
            } else {
                if (child != null)
                    setParent(node.right, replace);
                parent.left = child;
                replace.right = node.right;
                setParent(node.right, replace);
            }
            replace.parent = node.parent;
            replace.color = node.color;
            replace.left = node.left;
            node.left.parent = replace;

            if (color == BLACK)
                removeFixUp(child, parent);

            node = null;
            return;
        }

        if (node.left != null) {
            child = node.left;
        } else {
            child = node.right;
        }

        parent = node.parent;
        // 保存"取代节点"的颜色
        color = node.color;

        if (child != null)
            child.parent = parent;

        // "node节点"不是根节点
        if (parent != null) {
            if (parent.left == node)
                parent.left = child;
            else
                parent.right = child;
        } else {
            this.root = child;
        }

        if (color == BLACK)
            removeFixUp(child, parent);
        node = null;
    }

    /**
     * 红黑树删除修正函数
     * <p>
     * 在从红黑树中删除插入节点之后(红黑树失去平衡)，再调用该函数；
     * 目的是将它重新塑造成一颗红黑树。
     * <p>
     * 参数说明：
     * node 待修正的节点
     */
    private void removeFixUp(RBTNode<T> node, RBTNode<T> parent) {
        RBTNode<T> other;

        while ((node == null || isBlack(node)) && (node != this.root)) {
            if (parent.left == node) {
                other = parent.right;
                if (isRed(other)) {
                    // Case 1: x的兄弟w是红色的
                    setBlack(other);
                    setRed(parent);
                    leftRotate(parent);
                    other = parent.right;
                }

                if ((other.left == null || isBlack(other.left)) &&
                        (other.right == null || isBlack(other.right))) {
                    // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.right == null || isBlack(other.right)) {
                        // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。
                        setBlack(other.left);
                        setRed(other);
                        rightRotate(other);
                        other = parent.right;
                    }
                    // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.right);
                    leftRotate(parent);
                    node = this.root;
                    break;
                }
            } else {

                other = parent.left;
                if (isRed(other)) {
                    // Case 1: x的兄弟w是红色的
                    setBlack(other);
                    setRed(parent);
                    rightRotate(parent);
                    other = parent.left;
                }

                if ((other.left == null || isBlack(other.left)) &&
                        (other.right == null || isBlack(other.right))) {
                    // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.left == null || isBlack(other.left)) {
                        // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。
                        setBlack(other.right);
                        setRed(other);
                        leftRotate(other);
                        other = parent.left;
                    }

                    // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.left);
                    rightRotate(parent);
                    node = this.root;
                    break;
                }
            }
        }

        if (node != null)
            setBlack(node);
    }

    private RBTNode<T> parentOf(RBTNode<T> node) {
        return node != null ? node.parent : null;
    }

    private boolean colorOf(RBTNode<T> node) {
        return node != null ? node.color : BLACK;
    }

    private boolean isRed(RBTNode<T> node) {
        return ((node != null) && (node.color == RED)) ? true : false;
    }

    private boolean isBlack(RBTNode<T> node) {
        return !isRed(node);
    }

    private void setBlack(RBTNode<T> node) {
        if (node != null)
            node.color = BLACK;
    }

    private void setRed(RBTNode<T> node) {
        if (node != null)
            node.color = RED;
    }

    private void setParent(RBTNode<T> node, RBTNode<T> parent) {
        if (node != null)
            node.parent = parent;
    }

    private void setColor(RBTNode<T> node, boolean color) {
        if (node != null)
            node.color = color;
    }
}
