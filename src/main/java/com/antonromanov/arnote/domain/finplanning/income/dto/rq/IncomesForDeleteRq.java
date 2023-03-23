package com.antonromanov.arnote.domain.finplanning.income.dto.rq;

import com.antonromanov.arnote.domain.finplanning.income.dto.rq.IdListRq;
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
    // todo: тут надо проверить - а нам точно нужен список? Мы же вроде как по одной удаляем? И валидацию бы еще прикрутить по хорошему
    private List<IdListRq> idList; // Список айдюков для удаления.
}
