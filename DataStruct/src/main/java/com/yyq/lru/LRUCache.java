package com.yyq.lru;

import java.util.HashMap;

/**
 * 实现lru缓存失效算法
 *
 * @author yyq
 * @since 2018/12/06
 */
public class LRUCache<K, V> {
    class Entry<K, V> {
        public Entry pre;
        public Entry next;
        public K key;
        public V value;
    }

    private final int MAX_CACHE_SIZE;
    private Entry first;
    private Entry last;

    private HashMap<K, Entry<K, V>> map;

    public LRUCache(int capacity) {
        MAX_CACHE_SIZE = capacity;
        map = new HashMap<>();
    }

    private Entry getEntry(K key) {
        return map.get(key);
    }

    public void put(K key, V value) {
        Entry entry = getEntry(key);
        if (entry == null) {
            if (map.size() >= MAX_CACHE_SIZE) {
                map.remove(last.key);
                removeLast();
            }
            entry = new Entry();
            entry.key = key;
        }
        entry.value = value;
        moveToFirst(entry);
        map.put(key, entry);
    }

    public V get(K key) {
        Entry entry = map.get(key);
        if (entry == null) return null;
        moveToFirst(entry);
        return (V) entry.value;
    }

    private void removeLast() {
        if (last != null) {
            last = last.pre;
            if (last == null) first = null;
            else last.next = null;
        }
    }

    private void moveToFirst(Entry entry) {
        if (entry == first) return;
        if (entry.pre != null) entry.pre.next = entry.next;
        if (entry.next != null) entry.next.pre = entry.pre;
        if (entry == last) last = last.pre;
        if (first == null || last == null) {
            first = last = entry;
            return;
        }
        entry.next = first;
        first.pre = entry;
        first = entry;
        entry.pre = null;
    }


}
