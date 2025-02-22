package org.demo;

public interface Cache {

    String get(String key);
    void put(String key, String value);
}
