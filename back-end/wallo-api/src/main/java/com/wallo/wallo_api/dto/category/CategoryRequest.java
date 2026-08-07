package com.wallo.wallo_api.dto.category;

import com.wallo.wallo_api.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Dados recebidos para criar ou atualizar uma categoria.
 */
public record CategoryRequest(

        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotNull(message = "Tipo é obrigatório")
        CategoryType type
) {}