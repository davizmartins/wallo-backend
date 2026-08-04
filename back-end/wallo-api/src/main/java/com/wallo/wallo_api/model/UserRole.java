package com.wallo.wallo_api.model;

/**
 * Papéis de autorização reconhecidos pelo Spring Security.
 * O prefixo ROLE_ é exigido pela convenção do framework.
 */
public enum UserRole {
    ROLE_USER,
    ROLE_ADMIN
}