package org.example.service;

import lombok.AllArgsConstructor;
import org.example.repository.DataRepository;

@AllArgsConstructor
public class DataService {
    private final DataRepository dataRepository;

    public String processGetRequest(String key) {
        if (dataRepository.exists(key)) {
            return dataRepository.getData(key);
        }
        return null;
    }

    public String processPostRequest(String key, String value) {
        dataRepository.saveData(key, value);
        return "Data saved successfully: " + key;
    }

    public String getDefaultMessage() {
        return "Hello From Server!";
    }
}
