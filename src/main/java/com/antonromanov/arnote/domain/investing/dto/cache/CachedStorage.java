package com.antonromanov.arnote.domain.investing.dto.cache;

/**
 * Сущность, хранящая в кэше все нужные нам данные, полученные с биржи.
 *
 * Какие мы используем запросы:
 *
 * - calculateService.getCurrentQuote - Тек. цена бумаги - Optional<Double> -
 * - calculateService.getCurrency - Данные по валюте - String
 * - calculateService.getDividends - Дивиденды - ConsolidatedDividendsRs
 * - calculateService.getMinimalLot - Минимальный лот - Integer
 * - calculateService.calculateFinalPrice - Финальная цена - Integer
 * - calculateService.getDelta - дельта - DeltaRs
 * - calculateService.prepareBoardId - board_id - String
 * - calculateService.getInstrumentName - Имя инструмента - Optional<String>
 */
/*@AllArgsConstructor
@Data*/
public class CachedStorage {
   /* private final UUID uuid; // уникальной идентификатор записи
    private final int count; // попробуем использовать для сохранения статистики
    private final LocalTime creationTime;*/
}
