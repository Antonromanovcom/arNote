package com.antonromanov.arnote.domain.finplanning.common.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Консолидированные фин.планы.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinPlanListRs {
    private List<FinPlanRs> finPlans;
}
