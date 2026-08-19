package com.wallo.wallo_api.repository;

import com.wallo.wallo_api.model.Account;
import com.wallo.wallo_api.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Acesso a dados de Account, sempre no escopo do usuário dono.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUser(User user);

    Optional<Account> findByIdAndUser(Long id, User user);

    boolean existsByNameAndUser(String name, User user);
}