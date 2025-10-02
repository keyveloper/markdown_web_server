package org.example;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ResponseBuilder {

    public void sendResponse(OutputStream out, int statusCode, String statusMessage, String body) throws IOException {
        PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(out, StandardCharsets.UTF_8),
                true
        );

        // Status Line
        writer.print("HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n");

        // Headers
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        writer.print("Content-Type: text/html; charset=UTF-8\r\n");
        writer.print("Content-Length: " + bodyBytes.length + "\r\n");
        writer.print("Connection: close\r\n");
        writer.print("\r\n");
        writer.flush();

        // Body
        out.write(bodyBytes);
        out.flush();
    }

    public void sendOk(OutputStream out, String body) throws IOException {
        sendResponse(out, 200, "OK", body);
    }

    public void sendNotFound(OutputStream out) throws IOException {
        sendResponse(out, 404, "Not Found", "404 - Not Found");
    }

    public void sendBadRequest(OutputStream out) throws IOException {
        sendResponse(out, 400, "Bad Request", "400 - Bad Request");
    }

    public void sendInternalServerError(OutputStream out) throws IOException {
        sendResponse(out, 500, "Internal Server Error", "500 - Internal Server Error");
    }
}
