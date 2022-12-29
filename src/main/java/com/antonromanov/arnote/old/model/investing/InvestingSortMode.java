package com.antonromanov.arnote.old.model.investing;

import com.antonromanov.arnote.domain.investing.dto.response.BondRs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Comparator;

/**
 * Режим сортировки.
 */
@AllArgsConstructor
@Getter
public enum InvestingSortMode {
    TICKER_ASC("Сортировка по тикеру", null /*Comparator.comparing(BondRs::getTicker)*/),
    TICKER_DESC("Сортировка по тикеру обратная", null /*Comparator.comparing(BondRs::getTicker).reversed()*/),
    DIV_RUB_ASC("Сортировка по дивиденду в рублях", null /*Comparator.comparing(bondRs->{
        return bondRs.getDividends().getDivSum();
    })*/),
    DIV_RUB_DESC("Сортировка по дивиденду в рублях обратная", null /*Comparator.comparing(bondRs->{
        return bondRs.getDividends().getDivSum();
    }, Comparator.reverseOrder())*/),
    DIV_PRCNT_ASC("Сортировка по дивиденду в процентах", null /*Comparator.comparing(bondRs->{
        return bondRs.getDividends().getPercent();
    })*/),
    DIV_PRCNT_DESC("Сортировка по дивиденду в процентах обратная", null /*Comparator.comparing(bondRs->{
        return bondRs.getDividends().getPercent();
    }, Comparator.reverseOrder())*/),
    CUR_PRICE_ASC("Сортировка по текущей цене", null /*Comparator.comparing(BondRs::getCurrentPrice)*/),
    CUR_PRICE_DESC("Сортировка по текущей цене обратная", null /*Comparator.comparing(BondRs::getCurrentPrice).reversed()*/),
    FINAL_PRICE_ASC("Сортировка по общей цене", null /*Comparator.comparing(BondRs::getFinalPrice)*/),
    FINAL_PRICE_DESC("Сортировка по общей цене обратная", null /*Comparator.comparing(BondRs::getFinalPrice).reversed()*/),
    TOTAL_GROW_ASC("Сортировка по общему росту", null /*Comparator.comparing(bondRs->{
        return bondRs.getDelta().getDeltaInRubles();
    })*/),
    TOTAL_GROW_DESC("Сортировка по общему росту обратная", null /*Comparator.comparing(bondRs->{
        return bondRs.getDelta().getDeltaInRubles();
    }, Comparator.reverseOrder())*/),
    TODAY_GROW_ASC("Сортировка по сегодняшнему росту (по Тинькову)", null /*Comparator.comparing(bondRs->{
        return bondRs.getDelta().getTinkoffDelta();
    })*/),
    TODAY_GROW_DESC("Сортировка по общему росту обратная", null /*Comparator.comparing(bondRs->{
        return bondRs.getDelta().getTinkoffDelta();
    }, Comparator.reverseOrder())*/),

    NONE("Без сортировки", null /*Comparator.comparing(BondRs::getId)*/);


    private final String description;
    private final Comparator<BondRs> comparator;
}
