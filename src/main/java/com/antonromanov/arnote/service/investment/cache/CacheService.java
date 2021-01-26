package com.antonromanov.arnote.service.investment.cache;

import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import java.util.Optional;

/**
 * Сервис кеширования.
 */
public interface CacheService {
    void putBoardId(String ticker, String boardId);
    void evictAll();
    Optional<String> getBoardIdByTicker(String ticker);

    void putLastQuotes(String boardId, MoexDocumentRs doc);
    Optional<MoexDocumentRs> getQuotesByBoardId(String boardId);

    void putBondsByBoardsGroup(String boardGroup, MoexDocumentRs doc);
    Optional<MoexDocumentRs> getBondsByBoardGroup(String boardId);
}
