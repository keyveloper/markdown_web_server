package org.example;

import org.example.httphandler.HttpRequestParser;
import org.example.httphandler.RequestHandler;

public class Main {

    public static void main(String[] args) {
        // Initialize layers from bottom to top
        Server server = Server.getInstance();
        server.start();
    }
}
