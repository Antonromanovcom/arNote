package com.antonromanov.arnote.domain.investing.dto.cache;

import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.currentquote.MoexDocumentRs;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.util.Map;
import java.util.UUID;

/**
 * Сущность, хранящая в кэше последние ставки.
 *
 */
@AllArgsConstructor
@Data
public class CurrentQuoteCached {
    private final UUID uuid; // уникальной идентификатор записи
    private final long count; // уникальной идентификатор записи
    private Map<String, MoexDocumentRs> cache; // попробуем использовать для сохранения статистики
    private final LocalTime creationTime;
}
