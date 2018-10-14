package com.yyq.string;

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
}
