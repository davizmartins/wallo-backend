package com.wallo.wallo_api.repository;

import com.wallo.wallo_api.model.Category;
import com.wallo.wallo_api.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wallo.wallo_api.enums.CategoryType;

import java.util.Optional;

/**
 * Acesso a dados de Category, sempre no escopo do usuário dono.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /** Lista paginada das categorias de um usuário. */
    List<Category> findByUser(User user);
    /** Busca uma categoria por id, garantindo que pertence ao usuário. */
    Optional<Category> findByIdAndUser(Long id, User user);

    /** Impede categorias duplicadas (mesmo nome e tipo) para o mesmo usuário. */
    boolean existsByNameAndTypeAndUser(String name, CategoryType type, User user);
}
