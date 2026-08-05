package com.wallo.wallo_api.repository;

import com.wallo.wallo_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Acesso a dados da entidade User.
 * O Spring Data JPA implementa esta interface automaticamente em tempo de execução.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca um usuário pelo email (usado no login).
     * Retorna Optional pois o email pode não existir na base.
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica se já existe um usuário com o email informado
     * (usado no cadastro, para impedir emails duplicados).
     */
    boolean existsByEmail(String email);
}
