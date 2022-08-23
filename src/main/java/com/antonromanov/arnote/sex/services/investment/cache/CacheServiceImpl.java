package com.antonromanov.arnote.sex.services.investment.cache;

import com.antonromanov.arnote.model.investing.cache.enums.CacheDictType;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис кеширования.
 * TYPESAFE HETEROGENEOUS CONTAINERS PATTERN.
 */
/*@Service
@AllArgsConstructor
@Data
@Slf4j*/
public class CacheServiceImpl /*implements CacheService*/ {

 /*   private final Integer RETENTION_TIMEOUT = 60;
    private Map<String, MoexDocumentRs> history;
    private List<String> tradeModesStorage;
    private Map<CacheDictType, HashMap<String, Object>> cache;
    private Map<CacheDictType, HashMap<String, ObjectCachedWithTimestamp>> cacheWithRetention;



    @Override
    public void putTradeModes(List<String> tradeModes) {
        tradeModesStorage = tradeModes;
    }

    @Override
    public List<String> getTradeModes() {
        return tradeModesStorage;
    }

    *//**
     * Добавить объект в кэш.
     *
     * @param dictionaryType
     * @param obj
     * @param aClass
     * @param <T>
     *//*
    @Override
    public <T> void putToCache(CacheDictType dictionaryType, String key, T obj, Class<T> aClass) {
        CacheObject<T> cachedObject = new CacheObject<>(obj, aClass);
        HashMap<String, Object> cachedElement = new HashMap<>();
        cachedElement.put(key, cachedObject);
        if (cache.get(dictionaryType)!=null){
            HashMap<String, Object> mapSavedEarlier = cache.get(dictionaryType);
            mapSavedEarlier.put(key, cachedObject);
            cache.put(dictionaryType, mapSavedEarlier);
        } else {
            cache.put(dictionaryType, cachedElement);
        }
    }

    @Override
    public <T> T getDict(CacheDictType dictionaryType, String key) {
        CacheObject<T> o = ((CacheObject<T>) cache.get(dictionaryType).get(key));
        o.getClazz().cast(o.getT());
        return o.getClazz().cast(o.getT());
    }

    *//**
     * Проверка, что кэш не пустой по ключу и словарю.
     *
     * @param dictionaryType
     * @param key
     * @return
     *//*
    @Override
    public Boolean checkDict(CacheDictType dictionaryType, String key) {
        return dictionaryType != null &&
                key != null &&
                cache.get(dictionaryType) != null && cache.get(dictionaryType).get(key) != null;

    }

    @Override
    public <T> void putToCacheWithRetentionTime(CacheDictType dictionaryType, String key, T obj, Class<T> aClass, LocalDateTime timestamp) {
        CacheObject<T> cachedObject = new CacheObject<>(obj, aClass);
        HashMap<String, ObjectCachedWithTimestamp> cachedElement = new HashMap<>();
        ObjectCachedWithTimestamp objWithTimestamp = ObjectCachedWithTimestamp.builder().timestamp(timestamp).obj(cachedObject).build();
        cachedElement.put(key, objWithTimestamp);
        if (cacheWithRetention.get(dictionaryType)!=null){
            HashMap<String, ObjectCachedWithTimestamp> mapSavedEarlier = cacheWithRetention.get(dictionaryType);
            if (mapSavedEarlier.get(key)!=null &&
                    LocalDateTime.now().isBefore(mapSavedEarlier.get(key).timestamp.plusMinutes(RETENTION_TIMEOUT))) {
                mapSavedEarlier.put(key, objWithTimestamp);
                cacheWithRetention.put(dictionaryType, mapSavedEarlier);
            } else {
                cacheWithRetention.put(dictionaryType, cachedElement);
            }
        } else {
            cacheWithRetention.put(dictionaryType, cachedElement);
        }
    }

    @Override
    public <T> T getDictWithRetention(CacheDictType dictionaryType, String key) {
        log.warn("Достали из Кэша. dictionaryType: {}, key: {}", dictionaryType, key);
        ObjectCachedWithTimestamp objWithRetention =  cacheWithRetention.get(dictionaryType).get(key);
        CacheObject<T> o = (CacheObject<T>)(objWithRetention.obj);
        o.getClazz().cast(o.getT());
        return o.getClazz().cast(o.getT());
    }

    @Override
    public Boolean checkDictWithRetention(CacheDictType dictionaryType, String key) {
        return dictionaryType != null &&
                key != null &&
                cacheWithRetention.get(dictionaryType) != null &&
                cacheWithRetention.get(dictionaryType).get(key) != null &&
        cacheWithRetention.get(dictionaryType).get(key).timestamp != null &&
                LocalDateTime.now().isBefore(cacheWithRetention.get(dictionaryType).get(key).timestamp.plusMinutes(RETENTION_TIMEOUT));
    }

    @AllArgsConstructor
    @Data
    @Builder
    private static class ObjectCachedWithTimestamp {
        private LocalDateTime timestamp;
        private Object obj;
    }*/
}
