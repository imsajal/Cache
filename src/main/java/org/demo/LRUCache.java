package org.demo;

import java.util.HashMap;
import java.util.Map;

class Node {
    Node next;
    Node prev;
    String value;
    String key;

    Node(String key, String value) {
        this.key = key;
        this.value = value;
    }
}

// this can be implemented with using just one linked hash map
public class LRUCache implements Cache {

    int capacity;
    Map<String, Node> map;
    Node head;
    Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        head = new Node("0","123");
        tail = new Node("0", "123");
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public String get(String key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            remove(node);
            insert(node);
            return node.value;
        } else return "-1";
    }

    @Override
    public void putOrUpdate(String key, String value) {
        Node node = null;
        if (map.containsKey(key)) {
            node = map.get(key);
            node.value = value;
            remove(node);
        } else {
            if(capacity == map.size()) {
                // always update all the data structire created if needed at any time
                // first we should remove from map then from list, also we should remove by key,
                // so node shpuld have both key and value
                map.remove(head.next.key);
                remove(head.next);
            }
            node = new Node(key, value);
        }
        insert(node);
        map.put(key, node);
    }

    private void insert(Node node) {
            Node temp = tail.prev;
            tail.prev = node;
            node.next = tail;
            temp.next = node;
            node.prev = temp;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

}
