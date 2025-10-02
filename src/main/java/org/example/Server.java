package org.example;

import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@NoArgsConstructor
public class Server {

    public void start() {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName("127.0.0.1"))) {
            System.out.println("서버가 포트 " + port + "에서 대기 중...");

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("클라이언트가 연결됨: " + client.getInetAddress().getHostName());


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
