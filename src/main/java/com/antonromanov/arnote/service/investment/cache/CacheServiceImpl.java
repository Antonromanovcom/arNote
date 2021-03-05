package com.antonromanov.arnote.service.investment.cache;

import com.antonromanov.arnote.model.investing.cache.enums.CacheDictType;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис кеширования.
 * TYPESAFE HETEROGENEOUS CONTAINERS PATTERN.
 */
@Service
@AllArgsConstructor
@Data
public class CacheServiceImpl implements CacheService {

    private Map<String, MoexDocumentRs> history;
    private List<String> tradeModesStorage;
    private Map<CacheDictType, HashMap<String, Object>> cache;

    @Override
    public void putTradeModes(List<String> tradeModes) {
        tradeModesStorage = tradeModes;
    }

    @Override
    public List<String> getTradeModes() {
        return tradeModesStorage;
    }

    /**
     * Добавить объект в кэш.
     *
     * @param dictionaryType
     * @param obj
     * @param aClass
     * @param <T>
     */
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

    /**
     * Проверка, что кэш не пустой по ключу и словарю.
     *
     * @param dictionaryType
     * @param key
     * @return
     */
    @Override
    public Boolean checkDict(CacheDictType dictionaryType, String key) {
        return dictionaryType != null &&
                key != null &&
                cache.get(dictionaryType) != null && cache.get(dictionaryType).get(key) != null;

    }
}
