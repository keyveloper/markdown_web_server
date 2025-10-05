package org.example;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.example.controller.Controller;
import org.example.dto.ParsedHttpRequest;
import org.example.dto.Route;
import org.example.dto.ServerHttpResponse;
import org.example.httphandler.HttpResponseBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

    /**
     * 컨트롤러를 등록하고 인스턴스를 캐싱합니다.
     * @param controller 등록할 컨트롤러 인스턴스
     */
    public void registerController(Controller controller) {
        String controllerName = controller.getClass().getSimpleName();
        controllerInstances.put(controllerName, controller);
    }

    /**
     * 라우트를 수동으로 등록합니다.
     * @param uri URI 경로 (예: "/users/{id}")
     * @param method HTTP 메서드 (GET, POST, PUT, DELETE 등)
     * @param controller 컨트롤러 인스턴스
     * @param handlerMethodName 핸들러 메서드 이름
     */
    public void addRoute(String uri, String method, Controller controller, String handlerMethodName) {
        // 컨트롤러 등록
        registerController(controller);

        // URI 패턴 생성 (예: /users/{id} -> /users/([^/]+))
        Pattern uriPattern = createUriPattern(uri);

        // 경로 파라미터 추출 (예: {id}, {name})
        List<String> pathParamNames = extractPathParamNames(uri);

        Route route = new Route(uri, method, controller, handlerMethodName, uriPattern, pathParamNames);
        routes.add(route);
    }

    /**
     * GET 요청 라우트 등록 편의 메서드
     */
    public void get(String uri, Controller controller, String handlerMethodName) {
        addRoute(uri, "GET", controller, handlerMethodName);
    }

    /**
     * POST 요청 라우트 등록 편의 메서드
     */
    public void post(String uri, Controller controller, String handlerMethodName) {
        addRoute(uri, "POST", controller, handlerMethodName);
    }

    /**
     * PUT 요청 라우트 등록 편의 메서드
     */
    public void put(String uri, Controller controller, String handlerMethodName) {
        addRoute(uri, "PUT", controller, handlerMethodName);
    }

    /**
     * DELETE 요청 라우트 등록 편의 메서드
     */
    public void delete(String uri, Controller controller, String handlerMethodName) {
        addRoute(uri, "DELETE", controller, handlerMethodName);
    }

    /**
     * URI 패턴 생성: /users/{id} -> ^/users/([^/]+)$
     */
    private Pattern createUriPattern(String uri) {
        String regex = uri.replaceAll("\\{[^/]+\\}", "([^/]+)");
        return Pattern.compile("^" + regex + "$");
    }

    /**
     * 경로 파라미터 이름 추출: /users/{id}/posts/{postId} -> [id, postId]
     */
    private List<String> extractPathParamNames(String uri) {
        List<String> paramNames = new ArrayList<>();
        int start = 0;
        while ((start = uri.indexOf('{', start)) != -1) {
            int end = uri.indexOf('}', start);
            if (end == -1) break;
            paramNames.add(uri.substring(start + 1, end));
            start = end + 1;
        }
        return paramNames;
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