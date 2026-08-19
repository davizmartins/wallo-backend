package com.wallo.wallo_api.controller;

import com.wallo.wallo_api.dto.category.CategoryRequest;
import com.wallo.wallo_api.dto.category.CategoryResponse;
import com.wallo.wallo_api.model.User;
import com.wallo.wallo_api.security.UserDetailsImpl;
import com.wallo.wallo_api.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;

/**
 * Endpoints de gerenciamento de categorias do usuário autenticado.
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(
            @Valid @RequestBody CategoryRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        CategoryResponse response = categoryService.create(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> list(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(categoryService.list(userDetails.getUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        return ResponseEntity.ok(categoryService.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        categoryService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
