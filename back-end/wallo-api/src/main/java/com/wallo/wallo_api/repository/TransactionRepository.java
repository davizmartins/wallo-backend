package com.wallo.wallo_api.repository;

import com.wallo.wallo_api.model.Transaction;
import com.wallo.wallo_api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wallo.wallo_api.dto.dashboard.CategorySummary;
import com.wallo.wallo_api.enums.TransactionType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Acesso a dados de Transaction, sempre no escopo do usuário dono.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByUser(User user, Pageable pageable);

    Optional<Transaction> findByIdAndUser(Long id, User user);

    /**
     * Soma o valor das transações do usuário por categoria, dentro de um período e tipo.
     * Usado no dashboard (ex.: total de despesas por categoria).
     */
    @Query("""
            SELECT c.name AS categoryName, SUM(t.amount) AS total
            FROM Transaction t
            JOIN t.category c
            WHERE t.user = :user
              AND t.type = :type
              AND t.date BETWEEN :start AND :end
            GROUP BY c.name
            ORDER BY total DESC
            """)
    List<CategorySummary> sumByCategory(
            @Param("user") User user,
            @Param("type") TransactionType type,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}
