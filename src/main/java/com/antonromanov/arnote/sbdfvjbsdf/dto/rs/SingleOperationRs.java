package com.antonromanov.arnote.sbdfvjbsdf.dto.rs;

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
    ResponseStatusRs status;

}
