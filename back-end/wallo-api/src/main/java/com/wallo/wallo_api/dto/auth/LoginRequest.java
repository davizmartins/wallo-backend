package com.wallo.wallo_api.dto.auth;

import jakarta.validation.constraints.NotBlank;

/**
 * Credenciais recebidas no login.
 */
public record LoginRequest(

        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        String password
) {}
