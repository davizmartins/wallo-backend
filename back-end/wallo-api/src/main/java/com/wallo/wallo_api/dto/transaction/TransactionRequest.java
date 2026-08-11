package com.wallo.wallo_api.dto.transaction;

import com.wallo.wallo_api.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Dados recebidos para criar uma transação.
 * Referencia a conta e a categoria por seus ids.
 */
public record TransactionRequest(

        String description,

        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser positivo")
        BigDecimal amount,

        @NotNull(message = "Tipo é obrigatório")
        TransactionType type,

        @NotNull(message = "Data é obrigatória")
        LocalDate date,

        @NotNull(message = "Conta é obrigatória")
        Long accountId,

        @NotNull(message = "Categoria é obrigatória")
        Long categoryId
) {}