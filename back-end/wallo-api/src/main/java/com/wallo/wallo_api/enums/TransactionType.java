package com.wallo.wallo_api.enums;

/**
 * Tipo de transação: entrada (receita) ou saída (despesa) de dinheiro.
 */
public enum TransactionType {
    INCOME,   // receita: soma no saldo da conta
    EXPENSE   // despesa: subtrai do saldo da conta
}