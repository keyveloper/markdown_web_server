package org.example;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@AllArgsConstructor
public class Server {
    private final RequestHandler requestHandler;

    public void start() {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName("127.0.0.1"))) {
            System.out.println("서버가 포트 " + port + "에서 대기 중...");

            while (true) {
                Socket client = serverSocket.accept();
                requestHandler.handle(client);
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }
}
