package org.example;

import org.example.controller.Controller;
import org.example.dto.ParsedHttpRequest;
import org.example.dto.Route;
import org.example.dto.ServerHttpResponse;
import org.example.httphandler.HttpResponseBuilder;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Router {
    private static final Router INSTANCE = new Router();
    private final List<Route> routes = new ArrayList<>();
    private final Map<String, Controller> controllerInstances = new HashMap<>();

    private Router() {
        // Private constructor to prevent instantiation
    }

    public static Router getInstance() {
        return INSTANCE;
    }

    public ServerHttpResponse dispatch(ParsedHttpRequest parsedHttpRequest) throws IOException {
        // ✅ 1. find controller and method

        // ✅ 2. get httpResponse from ResponseBuilder
        HttpResponseBuilder httpResponseBuilder = new HttpResponseBuilder();



        return ServerHttpResponse.builder()
                .statusCode(200)
                .statusMessage("OK")
                .headers(Map.of("header1", "value1"))
                .body(new byte[1])
                .build();
    }
}