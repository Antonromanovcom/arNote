package com.antonromanov.arnote.service.investment;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Service
public class HighloadService {

    private int counter;

    @Cacheable(cacheNames = "recordsCache", key = "#recordId")
    public Record getOrCreateRecord(int recordId) {
        try {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("Записали в кэш запись с id = " + recordId);// запись будет создана в кеше только 1 раз
            counter = counter +1;
            return new Record(recordId, counter, LocalTime.now());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @CachePut(cacheNames = "recordsCache", key = "#recordId")
    public Record createOrUpdateRecord(int recordId) {
        // запись будет создаваться (обновляться) в кеше каждый раз
        return new Record(recordId, 0,LocalTime.now());
    }

    @CacheEvict(cacheNames = "recordsCache", key = "#recordId")
    public void deleteRecord(int recordId) {
        // запись будет удалена из кеша
    }
}
