package com.wallo.wallo_api.dto.dashboard;

import java.math.BigDecimal;

/**
 * Total movimentado agrupado por mês.
 */
public interface MonthlySummary {
    Integer getMonth();
    Integer getYear();
    BigDecimal getTotal();
}