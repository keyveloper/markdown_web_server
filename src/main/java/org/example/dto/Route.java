package org.example.dto;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.controller.Controller;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@AllArgsConstructor
@Getter
public class Route {
    private final String uri;
    private final String method;
    private final Controller controller;
    private final String handlerMethodName;
    private final Pattern uriPattern;
    private final List<String> pathParamNames;
}
