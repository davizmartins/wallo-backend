package com.wallo.wallo_api.model;

import com.wallo.wallo_api.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Transação financeira (receita ou despesa) de um usuário.
 * Movimenta o saldo da conta associada e é classificada por uma categoria.
 */
@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Descrição livre (ex.: "Mercado", "Salário de agosto")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "Tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    // Data em que a transação ocorreu (pode ser diferente da data de criação)
    @NotNull(message = "Data é obrigatória")
    @Column(nullable = false)
    private LocalDate date;

    // Conta afetada pela transação
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    // Categoria que classifica a transação
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Dono da transação (redundante via account, mas simplifica o isolamento nas queries)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}