package com.wallo.wallo_api.exception;

/**
 * Exceção para violações de regra de negócio (ex.: email já cadastrado).
 * Resulta em resposta HTTP 400 tratada pelo GlobalExceptionHandler.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
