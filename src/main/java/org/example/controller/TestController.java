package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.httphandler.HttpResponseBuilder;
import org.example.service.DataService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@AllArgsConstructor
public class TestController implements Controller{
    private final DataService dataService;
    private final HttpResponseBuilder httpResponseBuilder;

    public void route(String method, String uri, HashMap<String, String> headers, String body, OutputStream out) throws IOException {
        if ("GET".equalsIgnoreCase(method)) {
            handleGet(uri, out);
        } else if ("POST".equalsIgnoreCase(method)) {
            handlePost(uri, body, out);
        } else {
            httpResponseBuilder.sendBadRequest(out);
        }
    }

    private void handleGet(String uri, OutputStream out) throws IOException {
        if ("/".equals(uri)) {
            String responseBody = dataService.getDefaultMessage();
            httpResponseBuilder.sendOk(out, responseBody);
        } else if (uri.startsWith("/data/")) {
            String key = uri.substring("/data/".length());
            String data = dataService.processGetRequest(key);
            if (data != null) {
                httpResponseBuilder.sendOk(out, data);
            } else {
                httpResponseBuilder.sendNotFound(out);
            }
        } else {
            httpResponseBuilder.sendNotFound(out);
        }
    }

    private void handlePost(String uri, String body, OutputStream out) throws IOException {
        if (uri.startsWith("/data/")) {
            String key = uri.substring("/data/".length());
            String responseBody = dataService.processPostRequest(key, body);
            httpResponseBuilder.sendOk(out, responseBody);
        } else {
            httpResponseBuilder.sendNotFound(out);
        }
    }
}
