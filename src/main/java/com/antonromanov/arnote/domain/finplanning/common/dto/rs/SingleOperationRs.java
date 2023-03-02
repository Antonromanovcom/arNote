package com.antonromanov.arnote.domain.finplanning.common.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Ответ при одиночной операции с доходом
 */

@Data
@Builder
@AllArgsConstructor
public class SingleOperationRs {
    private Long id;
}
