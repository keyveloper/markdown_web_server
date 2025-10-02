package org.example;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
