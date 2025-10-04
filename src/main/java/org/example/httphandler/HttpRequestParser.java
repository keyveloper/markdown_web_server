package org.example.httphandler;

import org.example.dto.ParsedHttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {
    private static final HttpRequestParser INSTANCE = new HttpRequestParser();

    private HttpRequestParser() {
        // private constructor to prevent instantiation
    }

    public static HttpRequestParser getInstance() {
        return INSTANCE;
    }

    //🔑 Focus on Parsing
    public ParsedHttpRequest parse(BufferedReader reader) throws IOException {

        // ✅ 1. Parse: Request Line
        String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IOException("Invalid request line");
        }

        String[] requestParts = requestLine.split(" ");
        if (requestParts.length != 3) {
            throw new IOException("Invalid request format");
        }

        String method = requestParts[0];
        String uri = requestParts[1];
        String version = requestParts[2];

        // ✅ 2. Parse: Header
        Map<String, String> headers = parseHeaders(reader);

        // ✅ 3. Parse: Body
        String body = parseBody(reader, headers);

        return ParsedHttpRequest.builder()
                .method(method)
                .uri(uri)
                .headers((HashMap<String, String>) headers)
                .body(body)
                .build();
    }


    public Map<String, String> parseHeaders(BufferedReader reader) throws IOException {
        // headerLine example:
        // Host: localhost:8080
        // User-Agent: Mozilla/5.0
        // Accept: text/html
        Map<String, String> headers = new HashMap<>();
        String line;

        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            String[] parts = line.split(": ", 2);
            if (parts.length == 2) {
                headers.put(parts[0], parts[1]);
            }
        }

        return headers;
    }

    private String parseBody(BufferedReader reader, Map<String, String> headers)
            throws IOException {
        String contentLengthStr = headers.get("Content-Length");
        if (contentLengthStr == null) {
            return "";
        }

        int contentLength = Integer.parseInt(contentLengthStr);
        char[] bodyChars = new char[contentLength];
        reader.read(bodyChars, 0, contentLength);

        // convert to Json

        return new String(bodyChars);
    }
}
