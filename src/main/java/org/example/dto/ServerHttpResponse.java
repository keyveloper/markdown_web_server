package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Builder
@Getter
public class ServerHttpResponse {
    private int statusCode;
    private String statusMessage;
    private Map<String, String> headers;
    private byte[] body;
}
