package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.ResponseBuilder;
import org.example.service.DataService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@AllArgsConstructor
public class Controller {
    private final DataService dataService;
    private final ResponseBuilder responseBuilder;

    public void route(String method, String uri, HashMap<String, String> headers, String body, OutputStream out) throws IOException {
        if ("GET".equalsIgnoreCase(method)) {
            handleGet(uri, out);
        } else if ("POST".equalsIgnoreCase(method)) {
            handlePost(uri, body, out);
        } else {
            responseBuilder.sendBadRequest(out);
        }
    }

    private void handleGet(String uri, OutputStream out) throws IOException {
        if ("/".equals(uri)) {
            String responseBody = dataService.getDefaultMessage();
            responseBuilder.sendOk(out, responseBody);
        } else if (uri.startsWith("/data/")) {
            String key = uri.substring("/data/".length());
            String data = dataService.processGetRequest(key);
            if (data != null) {
                responseBuilder.sendOk(out, data);
            } else {
                responseBuilder.sendNotFound(out);
            }
        } else {
            responseBuilder.sendNotFound(out);
        }
    }

    private void handlePost(String uri, String body, OutputStream out) throws IOException {
        if (uri.startsWith("/data/")) {
            String key = uri.substring("/data/".length());
            String responseBody = dataService.processPostRequest(key, body);
            responseBuilder.sendOk(out, responseBody);
        } else {
            responseBuilder.sendNotFound(out);
        }
    }
}
