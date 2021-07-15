package com.antonromanov.arnote.service.investment.cache;

import com.antonromanov.arnote.domain.investing.dto.cache.enums.CacheDictType;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис кеширования.
 */
public interface CacheService {

    /**
     * Режимы торгов хранятся в списке и без ключа. Поэтому оставим эти методы для них.
     * @param tradeModes
     */
    void putTradeModes(List<String> tradeModes);
    List<String> getTradeModes();


//   ================  МЕТОДЫ ДЛЯ РАБОТЫ С КЭШОМ БЕЗ ВРЕМЕНИ ХРАНЕНИЯ ====================

    /**
     * Добавить объект в кэш.
     *
     * @param dictionaryType - тип словаря. То есть, что именно сохраняем, какой справочник.
     * @param obj - собственно сам инстанс объекта, который сохраняем.
     * @param aClass - класс инстанса.
     * @param key - ключ по которому сохраняем. Например борды по тикеру.
     */
    <T> void putToCache(CacheDictType dictionaryType, String key, T obj, Class<T> aClass);

    /**
     * Достать справочник из кэша
     *
     * @param dictionaryType
     *
     *
     */
    <T> T getDict(CacheDictType dictionaryType, String key);

    /**
     * Проверка, что кэш не пустой по ключу и словарю.
     * @param dictionaryType
     * @param key
     * @return
     */
    Boolean checkDict(CacheDictType dictionaryType, String key);

    //   ===================  МЕТОДЫ ДЛЯ РАБОТЫ С КЭШОМ С ПРОТУХАНИЕМ ПО ВРЕМЕНИ ====================

    /**
     * Добавить объект в кэш с временем хранения.
     *
     * @param dictionaryType - тип словаря. То есть, что именно сохраняем, какой справочник.
     * @param obj - собственно сам инстанс объекта, который сохраняем.
     * @param aClass - класс инстанса.
     * @param key - ключ по которому сохраняем. Например борды по тикеру.
     */
    <T> void putToCacheWithRetentionTime(CacheDictType dictionaryType, String key, T obj, Class<T> aClass, LocalDateTime timestamp);

    /**
     * Достать справочник с ограничением по времени из кэша
     *
     * @param dictionaryType
     *
     *
     */
    <T> T getDictWithRetention(CacheDictType dictionaryType, String key);

    /**
     * Проверка, что кэш не пустой по ключу и словарю и что он не протух.
     * @param dictionaryType
     * @param key
     * @return
     */
    Boolean checkDictWithRetention(CacheDictType dictionaryType, String key);
}
