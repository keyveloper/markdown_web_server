package org.example;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
public class Server {
    private final HttpRequestParser httpRequestParser;

    public void start() {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName("127.0.0.1"))) {
            System.out.println("서버가 포트 " + port + "에서 대기 중...");

            while (true) {
                Socket client = serverSocket.accept();
                InputStream in = client.getInputStream();
                OutputStream out = client.getOutputStream();
                System.out.println("클라이언트가 연결됨: " + client.getInetAddress().getHostName());

                // input
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in, StandardCharsets.UTF_8)
                );
                String requestLine = reader.readLine();

                // get requestLine parts
                List<String> requestParts = httpRequestParser.parseRequestLine(requestLine);

                // parse URI

                // Read header lines
                List<String> headerLines = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    headerLines.add(line);
                }
                HashMap<String, String> headers = httpRequestParser.parseHeaders(headerLines);

                // ⚠️ missing error handling
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

                    String body = new String(bodyBuf, 0, totalRead);
                    System.out.println("Body: " + body);
                }

                System.out.println("requestLine: " + requestParts.toString());
                System.out.println("Headers: " + headers);
                PrintWriter writer = new PrintWriter(
                        new OutputStreamWriter(out, StandardCharsets.UTF_8),
                        true
                );

                // Status Line
                writer.print("HTTP/1.1 200 OK\r\n");

                // Headers
                String body = "Hello From Server!";
                byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
                writer.print("Content-Type: text/html; charset=UTF-8\r\n");
                writer.print("Content-Length: " + bodyBytes.length + "\r\n");
                writer.print("Connection: close\r\n");
                writer.print("\r\n");  // 빈 줄
                writer.flush(); // Header 먼저 보내기

                out.write(bodyBytes);
                out.flush();

                client.setSoTimeout(5000);
                System.out.println("클라이언트 연결을 닫습니다");
                client.close();
                System.out.println("클라이언트 연결 종료?: " + client.isClosed());
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
}
