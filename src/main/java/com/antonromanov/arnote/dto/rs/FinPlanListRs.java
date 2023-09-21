package com.antonromanov.arnote.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * Консолидированные фин.планы.
 */
@Data
@Builder
@AllArgsConstructor
public class FinPlanListRs {
    private List<FinPlanRs> finPlans;
}