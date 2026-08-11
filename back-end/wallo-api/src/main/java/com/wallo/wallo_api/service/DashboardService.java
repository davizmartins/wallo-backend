package com.wallo.wallo_api.service;

import com.wallo.wallo_api.dto.dashboard.CategorySummary;
import com.wallo.wallo_api.enums.TransactionType;
import com.wallo.wallo_api.model.User;
import com.wallo.wallo_api.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import com.wallo.wallo_api.dto.dashboard.MonthlySummary;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Fornece dados agregados para os dashboards do usuário.
 */
@Service
public class DashboardService {

    private final TransactionRepository transactionRepository;

    public DashboardService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /** Total por categoria, para um tipo (receita/despesa) e período. */
    public List<CategorySummary> summaryByCategory(User user, TransactionType type,
                                                   LocalDate start, LocalDate end) {
        return transactionRepository.sumByCategory(user, type, start, end);
    }

    /** Total de um tipo (receita/despesa) no período. */
    public BigDecimal totalByType(User user, TransactionType type, LocalDate start, LocalDate end) {
        return transactionRepository.sumByType(user, type, start, end);
    }

    /** Evolução mensal de um tipo. */
    public List<MonthlySummary> monthlySummary(User user, TransactionType type) {
        return transactionRepository.sumByMonth(user, type);
    }
}