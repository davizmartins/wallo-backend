package com.wallo.wallo_api.service;

import com.wallo.wallo_api.dto.category.CategoryRequest;
import com.wallo.wallo_api.dto.category.CategoryResponse;
import com.wallo.wallo_api.exception.BusinessException;
import com.wallo.wallo_api.model.Category;
import com.wallo.wallo_api.model.User;
import com.wallo.wallo_api.repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Regras de negócio de categorias, sempre no escopo do usuário autenticado.
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /** Cria uma categoria para o usuário. */
    public CategoryResponse create(CategoryRequest request, User user) {
        if (categoryRepository.existsByNameAndTypeAndUser(request.name(), request.type(), user)) {
            throw new BusinessException("Já existe uma categoria com esse nome e tipo");
        }

        Category category = new Category();
        category.setName(request.name());
        category.setType(request.type());
        category.setUser(user);

        Category saved = categoryRepository.save(category);
        return CategoryResponse.fromEntity(saved);
    }

    /** Lista paginada das categorias do usuário. */
    public List<CategoryResponse> list(User user) {
        return categoryRepository.findByUser(user)
                .stream()
                .map(CategoryResponse::fromEntity)
                .toList();
    }

    /** Atualiza uma categoria existente do usuário. */
    public CategoryResponse update(Long id, CategoryRequest request, User user) {
        Category category = categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BusinessException("Categoria não encontrada"));

        category.setName(request.name());
        category.setType(request.type());

        Category updated = categoryRepository.save(category);
        return CategoryResponse.fromEntity(updated);
    }

    /** Remove uma categoria do usuário. */
    public void delete(Long id, User user) {
        Category category = categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new BusinessException("Categoria não encontrada"));

        categoryRepository.delete(category);
    }
}