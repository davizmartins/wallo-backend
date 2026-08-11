package com.wallo.wallo_api.dto.transaction;

import com.wallo.wallo_api.enums.TransactionType;
import com.wallo.wallo_api.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Dados de uma transação retornados pela API.
 */
public record TransactionResponse(
        Long id,
        String description,
        BigDecimal amount,
        TransactionType type,
        LocalDate date,
        Long accountId,
        String accountName,
        Long categoryId,
        String categoryName
) {
    public static TransactionResponse fromEntity(Transaction t) {
        return new TransactionResponse(
                t.getId(),
                t.getDescription(),
                t.getAmount(),
                t.getType(),
                t.getDate(),
                t.getAccount().getId(),
                t.getAccount().getName(),
                t.getCategory().getId(),
                t.getCategory().getName()
        );
    }
}