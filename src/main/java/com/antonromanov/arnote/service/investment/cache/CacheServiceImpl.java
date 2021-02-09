package com.antonromanov.arnote.service.investment.cache;

import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис кеширования.
 */
@Service
@AllArgsConstructor
@Data
public class CacheServiceImpl implements CacheService {

    private Map<String, String> boardIdMap;
    private Map<String, MoexDocumentRs> quotesMap;
    private Map<String, MoexDocumentRs> bondsAndBoards;
    private Map<String, MoexDocumentRs> history;
    private List<String> tradeModesStorage;

    @Override
    public void putBoardId(String ticker, String boardId) {
        boardIdMap.put(ticker, boardId);
    }

    @Override
    public void evictAll() {
        boardIdMap.clear();
    }

    @Override
    public Optional<String> getBoardIdByTicker(String ticker) {
        return Optional.ofNullable(boardIdMap.get(ticker));
    }

  @Override
  public void putLastQuotes(String boardId, MoexDocumentRs doc) {
    quotesMap.put(boardId, doc);
  }

  @Override
  public Optional<MoexDocumentRs> getQuotesByBoardId(String boardId) {
    return Optional.ofNullable(quotesMap.get(boardId));
  }

  @Override
  public void putBondsByBoardsGroup(String boardGroup, MoexDocumentRs doc) {
      bondsAndBoards.put(boardGroup, doc);
  }

  @Override
  public Optional<MoexDocumentRs> getBondsByBoardGroup(String boardId) {
      return Optional.ofNullable(bondsAndBoards.get(boardId));
  }
    @Override
    public void putTradeModes(List<String> tradeModes) {
        tradeModesStorage = tradeModes;

    }

    @Override
    public List<String> getTradeModes() {
        return tradeModesStorage;
    }

    @Override
    public void putHistory(String key, MoexDocumentRs doc) {
        history.put(key, doc);
    }

    @Override
    public Optional<MoexDocumentRs> getHistory(String key) {
        return Optional.ofNullable(history.get(key));
    }
}
