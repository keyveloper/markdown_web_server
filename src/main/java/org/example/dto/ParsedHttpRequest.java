package org.example.dto;

import lombok.Builder;

import java.util.HashMap;

@Builder
public record ParsedHttpRequest(
        String uri,
        String method,
        String body,
        HashMap<String, String> headers
) {
}
