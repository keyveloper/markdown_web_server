package org.example.httphandler;

import org.example.Router;
import org.example.dto.ParsedHttpRequest;
import org.example.dto.ServerHttpResponse;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class RequestHandler {
    private static final RequestHandler INSTANCE = new RequestHandler();
    private final HttpRequestParser httpRequestParser = HttpRequestParser.getInstance();
    private final Router router = Router.getInstance();

    public static RequestHandler getInstance() {
        return INSTANCE;
    }

    public void handle(Socket client) {
        try (client) {
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            System.out.println("클라이언트가 연결됨: " + client.getInetAddress().getHostName());

            // 🔑 HTTP Request Read & Parsing
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)
            );
            ParsedHttpRequest parsedHttpRequest = httpRequestParser.parse(reader);


            // 🔑 send Http Response to Client
            ServerHttpResponse serverHttpResponse = router.dispatch(parsedHttpRequest);


            client.setSoTimeout(5000);
            System.out.println("클라이언트 연결을 닫습니다");
        } catch (IOException e) {
            System.err.println("Request handling error: " + e.getMessage());
        }
    }
}
