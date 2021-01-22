package com.antonromanov.arnote.service.investment.cache;

import com.antonromanov.arnote.model.investing.cache.CurrentQuoteCached;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//@Service
//@Slf4j
public class CacheService {

   /* private long counter;

    @Cacheable(cacheNames = "lastQuoteCache", key = "#boardId")
    public CurrentQuoteCached getOrCreateLastQuote(String boardId, MoexDocumentRs data) {

        counter = counter + 1;
        log.info("Записали в кэш запись (last_quote) с id  {}. Кол-во обращений к кэшу за 15 мин: {}", boardId, counter);

        Map<String, MoexDocumentRs> map = new HashMap<>();
        map.put(boardId, data);
        return new CurrentQuoteCached(UUID.randomUUID(), counter, map, LocalTime.now());
    }

    @Cacheable(cacheNames = "lastQuoteCache", key = "#boardId")
    public CurrentQuoteCached justGetById(String boardId) {
        log.info("Пытаемся достать из кэша запись (last_quote) с id  {}. Кол-во обращений к кэшу за 15 мин: {}", boardId, counter);

        Map<String, MoexDocumentRs> map = new HashMap<>();
        map.put(boardId, null);
        return new CurrentQuoteCached(UUID.randomUUID(), counter, map, LocalTime.now());
    }


    @CachePut(cacheNames = "lastQuoteCache", key = "#boardId")
    public CurrentQuoteCached createOrUpdateRecord(String boardId, MoexDocumentRs data) {
        log.info("Записали в кэш запись (last_quote) с id  {}. Кол-во обращений к кэшу за 15 мин: {}", boardId, counter);
        Map<String, MoexDocumentRs> map = new HashMap<>();
        map.put(boardId, data);
        return new CurrentQuoteCached(UUID.randomUUID(), counter, map, LocalTime.now());
    }*/

    /*
    @CacheEvict(cacheNames = "recordsCache", key = "#recordId")
    public void deleteRecord(int recordId) {
        // запись будет удалена из кеша
    }*/
}
