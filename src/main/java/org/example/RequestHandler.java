package org.example;

import lombok.AllArgsConstructor;
import org.example.controller.Controller;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class RequestHandler {
    private final HttpRequestParser httpRequestParser;
    private final Controller controller;

    public void handle(Socket client) {
        try (client) {
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            System.out.println("클라이언트가 연결됨: " + client.getInetAddress().getHostName());

            // Parse HTTP Request
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)
            );
            String requestLine = reader.readLine();

            // Validate request line
            if (requestLine == null || requestLine.isEmpty()) {
                System.err.println("Invalid request line");
                return;
            }

            // Get requestLine parts
            List<String> requestParts = httpRequestParser.parseRequestLine(requestLine);

            if (requestParts == null || requestParts.size() != 3) {
                System.err.println("Failed to parse request line");
                return;
            }

            String method = requestParts.get(0);
            String uri = requestParts.get(1);
            String version = requestParts.get(2);

            // Read header lines
            List<String> headerLines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                headerLines.add(line);
            }
            HashMap<String, String> headers = httpRequestParser.parseHeaders(headerLines);

            // Read body if Content-Length exists
            String body = "";
            String contentLengthStr = headers.get("Content-Length");
            if (contentLengthStr != null) {
                int contentLength = Integer.parseInt(contentLengthStr);
                char[] bodyBuf = new char[contentLength];
                int totalRead = 0;

                while (totalRead < contentLength) {
                    int charsRead = reader.read(bodyBuf, totalRead, contentLength - totalRead);
                    if (charsRead == -1) break;
                    totalRead += charsRead;
                }

                body = new String(bodyBuf, 0, totalRead);
                System.out.println("Body: " + body);
            }

            System.out.println("Method: " + method + ", URI: " + uri + ", Version: " + version);
            System.out.println("Headers: " + headers);

            // Route request to controller
            controller.route(method, uri, headers, body, out);

            client.setSoTimeout(5000);
            System.out.println("클라이언트 연결을 닫습니다");
        } catch (IOException e) {
            System.err.println("Request handling error: " + e.getMessage());
        }
    }
}
