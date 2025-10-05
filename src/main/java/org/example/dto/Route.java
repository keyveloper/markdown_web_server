package org.example.dto;

import lombok.*;
import org.example.controller.Controller;

import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Route {
    private final String uri;
    private final String method;
    private final Controller controller;
    private final String handlerMethodName;
    private final Pattern uriPattern;
    private final List<String> pathParamNames;
}
