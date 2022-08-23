package com.antonromanov.arnote.domain.investing.dto.common;

/**
 * Режим сортировки.
 */
/*@AllArgsConstructor
@Getter*/
public enum InvestingSortMode {
 /*   TICKER_ASC("Сортировка по тикеру", Comparator.comparing(BondRs::getTicker)),
    TICKER_DESC("Сортировка по тикеру обратная", Comparator.comparing(BondRs::getTicker).reversed()),
    DIV_RUB_ASC("Сортировка по дивиденду в рублях", Comparator.comparing(bondRs->{
        return bondRs.getDividends().getDivSum();
    })),
    DIV_RUB_DESC("Сортировка по дивиденду в рублях обратная", Comparator.comparing(bondRs->{
        return bondRs.getDividends().getDivSum();
    }, Comparator.reverseOrder())),
    DIV_PRCNT_ASC("Сортировка по дивиденду в процентах", Comparator.comparing(bondRs->{
        return bondRs.getDividends().getPercent();
    })),
    DIV_PRCNT_DESC("Сортировка по дивиденду в процентах обратная", Comparator.comparing(bondRs->{
        return bondRs.getDividends().getPercent();
    }, Comparator.reverseOrder())),
    CUR_PRICE_ASC("Сортировка по текущей цене", Comparator.comparing(BondRs::getCurrentPrice)),
    CUR_PRICE_DESC("Сортировка по текущей цене обратная", Comparator.comparing(BondRs::getCurrentPrice).reversed()),
    FINAL_PRICE_ASC("Сортировка по общей цене", Comparator.comparing(BondRs::getFinalPrice)),
    FINAL_PRICE_DESC("Сортировка по общей цене обратная", Comparator.comparing(BondRs::getFinalPrice).reversed()),
    TOTAL_GROW_ASC("Сортировка по общему росту", Comparator.comparing(bondRs->{
        return bondRs.getDelta().getDeltaInRubles();
    })),
    TOTAL_GROW_DESC("Сортировка по общему росту обратная", Comparator.comparing(bondRs->{
        return bondRs.getDelta().getDeltaInRubles();
    }, Comparator.reverseOrder())),
    TODAY_GROW_ASC("Сортировка по сегодняшнему росту (по Тинькову)", Comparator.comparing(bondRs->{
        return bondRs.getDelta().getTinkoffDelta();
    })),
    TODAY_GROW_DESC("Сортировка по общему росту обратная", Comparator.comparing(bondRs->{
        return bondRs.getDelta().getTinkoffDelta();
    }, Comparator.reverseOrder())),

    NONE("Без сортировки", Comparator.comparing(BondRs::getId));


    private final String description;
    private final Comparator<BondRs> comparator;*/
}
