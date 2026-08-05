package com.wallo.wallo_api.dto.auth;

/**
 * Resposta de autenticação: devolve o token JWT gerado.
 * O campo "type" indica o esquema de autenticação (padrão Bearer).
 */
public record AuthResponse(
        String token,
        String type
) {
    public AuthResponse(String token) {
        this(token, "Bearer");
    }
}
