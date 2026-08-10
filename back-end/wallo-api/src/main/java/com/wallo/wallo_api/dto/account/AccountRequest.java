package com.wallo.wallo_api.dto.account;

import com.wallo.wallo_api.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Dados recebidos para criar ou atualizar uma conta.
 */
public record AccountRequest(

        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotNull(message = "Tipo é obrigatório")
        AccountType type,

        // Saldo inicial opcional; se nulo, assume zero no service
        BigDecimal initialBalance
) {}