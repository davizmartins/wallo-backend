package com.wallo.wallo_api.repository;

import com.wallo.wallo_api.model.Transaction;
import com.wallo.wallo_api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Acesso a dados de Transaction, sempre no escopo do usuário dono.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByUser(User user, Pageable pageable);

    Optional<Transaction> findByIdAndUser(Long id, User user);
}