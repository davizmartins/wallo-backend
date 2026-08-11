package com.wallo.wallo_api.service;

import com.wallo.wallo_api.dto.dashboard.CategorySummary;
import com.wallo.wallo_api.enums.TransactionType;
import com.wallo.wallo_api.model.User;
import com.wallo.wallo_api.repository.TransactionRepository;
import org.springframework.stereotype.Service;

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
}