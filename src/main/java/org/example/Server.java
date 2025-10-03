package org.example;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                System.out.println("클라이언트가 연결됨: " + client.getInetAddress().getHostName());

                // input
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8)
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

                System.out.println("Headers: " + headers);

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
