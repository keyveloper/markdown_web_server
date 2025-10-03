package org.example;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
public class HttpRequestParser {

    public List<String> parseRequestLine(String requestLine) {
        // BufferedReader => String으로 바로 읽어들임
        // requestLine example:
        // GET /index.html HTTP/1.1\r\n

        String[] requestLineParts = requestLine.split(" ");
        if (requestLineParts.length != 3) {
            return null;
        }

        // requestLineParts[0] = method (GET, POST, etc.)
        // requestLineParts[1] = path (/index.html)
        // requestLineParts[2] = version (HTTP/1.1)
        return List.of(requestLineParts[0], requestLineParts[1], requestLineParts[2].trim());
    }

    public HashMap<String, String> parseHeaders(List<String> headerLines) {
        // headerLine example:
        // Host: localhost:8080
        // User-Agent: Mozilla/5.0
        // Accept: text/html

        HashMap<String, String> headers = new HashMap<>();

        for (String line : headerLines) {
            if (line == null || line.trim().isEmpty()) {
                continue;
            }

            int colonIndex = line.indexOf(":");
            if (colonIndex > 0) {
                String key = line.substring(0, colonIndex).trim();
                String value = line.substring(colonIndex + 1).trim();
                headers.put(key, value);
            }
        }

        return headers;
    }
}
