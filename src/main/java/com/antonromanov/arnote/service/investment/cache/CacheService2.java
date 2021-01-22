package com.antonromanov.arnote.service.investment.cache;

import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

//@Service
//@Slf4j
public class CacheService2 {

    /**
     * Кэш с последними ставками.
     * key - boar_id.
     */
   // private HashMap<String, MoexDocumentRs> lastQuoteCache;

    /**
     * Кэш инициирован или нет?
     */
   /* @Getter
    private Boolean isInit = false;*/

    /**
     * Таймстемп предыдущего рефреша.
     */
  //  private LocalDateTime timeStamp;

    /**
     * Инициация кэша.
     */
  /*  public void init() {
        this.lastQuoteCache = new HashMap<>();
        this.timeStamp = null;
        this.isInit = false;
    }*/

    /**
     * Выдать данные по последним ставкам по board_id.
     */
    /*public Optional<MoexDocumentRs> getLastQuoteDocumentByKey(String key) {
        if (this.isInit && !lastQuoteCache.isEmpty()){
            return Optional.of(this.lastQuoteCache.get(key));
        } else {
            return Optional.empty();
        }
    }*/

    /**
     * Выдать данные по последним ставкам по board_id, а если нет - положить в кэш.
     */
   /* public Optional<MoexDocumentRs> getOrCreateLastQuote(String key, MoexDocumentRs doc) {
        if (this.isInit && !lastQuoteCache.isEmpty()){
            return Optional.of(this.lastQuoteCache.get(key));
        } else {
            init();
            lastQuoteCache.put(key, doc);
            return Optional.of(lastQuoteCache.get(key));
        }
    }
*/
}
