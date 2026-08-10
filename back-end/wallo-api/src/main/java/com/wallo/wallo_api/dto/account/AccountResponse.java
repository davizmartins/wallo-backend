package com.wallo.wallo_api.dto.account;

import com.wallo.wallo_api.enums.AccountType;
import com.wallo.wallo_api.model.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Dados de uma conta retornados pela API.
 */
public record AccountResponse(
        Long id,
        String name,
        AccountType type,
        BigDecimal balance,
        LocalDateTime createdAt
) {
    public static AccountResponse fromEntity(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getName(),
                account.getType(),
                account.getBalance(),
                account.getCreatedAt()
        );
    }
}