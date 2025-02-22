package org.demo;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache implements Cache {

    private final int capacity;
    private int minFreq;
    private final Map<String, String> valueMap; // stores key -> value
    private final Map<String, Integer> freqMap; // stores key -> frequency
    private final Map<Integer, LinkedHashSet<String>> freqListMap; // frequency -> keys

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.valueMap = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.freqListMap = new HashMap<>();
    }

    @Override
    public String get(String key) {
        if (capacity == 0 || key == null || !valueMap.containsKey(key)) {
            return null;
        }
        increaseFrequency(key);
        return valueMap.get(key);
    }

    @Override
    public void putOrUpdate(String key, String value) {
        if (capacity == 0 || key == null) {
            return;
        }

        if (valueMap.containsKey(key)) {
            valueMap.put(key, value);
            increaseFrequency(key);
            return;
        }

        if (valueMap.size() >= capacity) {
            // Evict LFU key
            LinkedHashSet<String> keys = freqListMap.get(minFreq);
            String evictKey = keys.iterator().next(); // remove first inserted
            keys.remove(evictKey);
            if (keys.isEmpty()) {
                freqListMap.remove(minFreq);
            }
            valueMap.remove(evictKey);
            freqMap.remove(evictKey);
        }

        valueMap.put(key, value);
        freqMap.put(key, 1);
        freqListMap.computeIfAbsent(1, ignore -> new LinkedHashSet<>()).add(key);
        minFreq = 1;
    }

    private void increaseFrequency(String key) {
        int oldFreq = freqMap.get(key);
        int newFreq = oldFreq + 1;
        freqMap.put(key, newFreq);

        // remove from old frequency list
        LinkedHashSet<String> oldSet = freqListMap.get(oldFreq);
        oldSet.remove(key);
        if (oldSet.isEmpty()) {
            freqListMap.remove(oldFreq);
            if (minFreq == oldFreq) {
                minFreq = newFreq;
            }
        }

        // add to new frequency list
        freqListMap.computeIfAbsent(newFreq, ignore -> new LinkedHashSet<>()).add(key);
    }
}
