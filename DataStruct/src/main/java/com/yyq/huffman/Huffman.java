package com.yyq.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 哈夫曼树
 *
 * @author yyq
 * @since 2018/12/05
 */
public class Huffman {
    private class Node implements Comparable<Node> {
        char ch;
        int freq;
        boolean isLeaf;
        Node left, right;

        public Node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
            isLeaf = true;
        }

        public Node(Node left, Node right, int freq) {
            this.left = left;
            this.right = right;
            this.freq = freq;
            isLeaf = false;
        }

        @Override
        public int compareTo(Node o) {
            return this.freq - o.freq;
        }
    }

    public Map<Character, String> encode(Map<Character, Integer> freqForChar) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (Character ch : freqForChar.keySet()) {
            priorityQueue.add(new Node(ch, freqForChar.get(ch)));
        }
        while (priorityQueue.size() != 1) {
            Node n1 = priorityQueue.poll();
            Node n2 = priorityQueue.poll();
            priorityQueue.add(new Node(n1, n2, n1.freq + n2.freq));
        }
        return encode(priorityQueue.poll());
    }

    private Map<Character, String> encode(Node root) {
        Map<Character, String> encodingForChar = new HashMap<>();
        encode(root, "", encodingForChar);
        return encodingForChar;
    }

    private void encode(Node node, String encoding, Map<Character, String> encodingForChar) {
        if (node.isLeaf) {
            encodingForChar.put(node.ch, encoding);
            return;
        }
        encode(node.left, encoding + '0', encodingForChar);
        encode(node.right, encoding + '1', encodingForChar);

    }

}
