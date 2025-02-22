package org.demo;

public interface Cache {

    String get(String key);
    void putOrUpdate(String key, String value);
}
