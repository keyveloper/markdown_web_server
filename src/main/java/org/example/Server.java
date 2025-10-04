package org.example;

import org.example.httphandler.RequestHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final Server INSTANCE = new Server();
    private final RequestHandler requestHandler = RequestHandler.getInstance();;

    public static Server getInstance() {
        return INSTANCE;
    }

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
