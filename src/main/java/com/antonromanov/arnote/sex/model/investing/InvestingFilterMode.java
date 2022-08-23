package com.antonromanov.arnote.sex.model.investing;

/**
 * Режим фильтрации для инвест-части.
 */
/*@AllArgsConstructor
@Getter*/
public enum InvestingFilterMode {
   /* TYPE_SHARE("Акции", s->BondType.valueOf(s.getType())==BondType.SHARE, "type"),
    TYPE_BOND("Облигации", s->BondType.valueOf(s.getType())==BondType.BOND, "type"),
    TYPE_ETF("ETF", s->BondType.valueOf(s.getType())==BondType.INDEX, "type"),
    STATUS_PLAN("План", s->!s.getIsBought(), "status"),
    STATUS_FACT("Факт", BondRs::getIsBought, "status"),
    NONE("Без сортировки", null, null);

    private final String description;
    private final Predicate<BondRs> filter;
    private final String key; // ключ для мапы - показывает по какому столбцу делаем фильтрацию.*/
}
