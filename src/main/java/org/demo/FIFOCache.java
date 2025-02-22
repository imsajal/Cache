package org.demo;

public class FIFOCache implements Cache{

    int capacity;

    public FIFOCache(int capacity) {
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
