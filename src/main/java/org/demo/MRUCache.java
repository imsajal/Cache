package org.demo;

// this can be implemented using just one linkedhashmap with mainting reference to the last key
public class MRUCache implements Cache{

    int capacity;

    public MRUCache(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String get(String key) {
        return "";
    }

    @Override
    public void putOrUpdate(String key, String value) {

    }
}
