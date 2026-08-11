package com.wallo.wallo_api.dto.dashboard;

import java.math.BigDecimal;

/**
 * Resultado agregado: total movimentado por categoria.
 * Interface preenchida automaticamente pelo Spring a partir da query.
 */
public interface CategorySummary {
    String getCategoryName();
    BigDecimal getTotal();
}