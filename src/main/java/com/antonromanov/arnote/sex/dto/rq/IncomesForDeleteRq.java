package com.antonromanov.arnote.sex.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO для добавления нового дохода&.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomesForDeleteRq {
    private List<IdListRq> idList; // Список айдюков для удаления.
}
