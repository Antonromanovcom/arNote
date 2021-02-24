package com.antonromanov.arnote.service.investment.cache;

import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import java.util.List;
import java.util.Optional;

/**
 * Сервис кеширования.
 */
public interface CacheService {
    void putBoardId(String ticker, String boardId);
    Optional<String> getBoardIdByTicker(String ticker);

    void putLastQuotes(String boardId, MoexDocumentRs doc);
    Optional<MoexDocumentRs> getQuotesByBoardId(String boardId);

    void putBondsByBoardsGroup(String boardGroup, MoexDocumentRs doc);
    Optional<MoexDocumentRs> getBondsByBoardGroup(String boardId);

    void putTradeModes(List<String> tradeModes);
    List<String> getTradeModes();

    void putHistory(String key, MoexDocumentRs doc);
    Optional<MoexDocumentRs> getHistory(String key);

    /**
     * Добавить объект в кэш.
     *
     * @param dictionaryType - тип словаря. То есть, что именно сохраняем, какой справочник.
     * @param obj - собственно сам инстанс объекта, который сохраняем.
     * @param aClass - класс инстанса.
     * @param key - ключ по которому сохраняем. Например борды по тикеру.
     */
    <T> void putToCache(String dictionaryType, String key, T obj, Class<T> aClass);

    /**
     * Достать справочник из кэша
     *
     * @param dictionaryType
     *
     *
     */
    <T> T getDict(String dictionaryType, String key);
}
