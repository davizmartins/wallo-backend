package com.wallo.wallo_api.dto.category;

import com.wallo.wallo_api.enums.CategoryType;
import com.wallo.wallo_api.model.Category;

import java.time.LocalDateTime;

/**
 * Dados de uma categoria retornados pela API (sem expor o usuário dono).
 */
public record CategoryResponse(
        Long id,
        String name,
        CategoryType type,
        LocalDateTime createdAt
) {
    /** Converte a entidade em DTO de resposta. */
    public static CategoryResponse fromEntity(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getType(),
                category.getCreatedAt()
        );
    }
}