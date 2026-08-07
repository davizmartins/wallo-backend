package com.wallo.wallo_api.exception;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Estrutura padrão de resposta de erro da API.
 * O campo "fields" só é preenchido em erros de validação.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> fields
) {
    public ErrorResponse(int status, String error, String message) {
        this(LocalDateTime.now(), status, error, message, null);
    }
}
