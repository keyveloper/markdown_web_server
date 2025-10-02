package org.example.repository;

import java.util.HashMap;
import java.util.Map;

public class DataRepository {
    private final Map<String, String> dataStore = new HashMap<>();

    public DataRepository() {
        // Initialize with some sample data
        dataStore.put("sample", "This is sample data from repository");
    }

    public String getData(String key) {
        return dataStore.get(key);
    }

    public void saveData(String key, String value) {
        dataStore.put(key, value);
    }

    public boolean exists(String key) {
        return dataStore.containsKey(key);
    }
}
