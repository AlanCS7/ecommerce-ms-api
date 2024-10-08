package dev.alancss.ecommerce.handler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        int status,
        String message,
        OffsetDateTime timestamp,
        String path,
        Map<String, String> errors
) {
}