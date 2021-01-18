package com.antonromanov.arnote.model.investing.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestTemplateOperation {
    GET_DIVS_MOEX(StockExchange.MOEX, "http://iss.moex.com/iss/securities/%s/dividends.xml?iss.meta=off", "Class"),
    GET_LAST_QUOTE_MOEX(StockExchange.MOEX, "https://iss.moex.com/iss/engines/stock/markets/shares/boards/" +
            "TQBR/securities.xml?iss.dp=comma&iss.meta=off&iss.only=securities&securities.columns=SECID," +
            "PREVADMITTEDQUOTE", "Class"),
    GET_INSTRUMENT_DETAIL_INFO(StockExchange.MOEX, "http://iss.moex.com/iss/history/engines/stock/markets/shares/securities/%s", "Class");


    private final StockExchange stockExchange;
    private final String url;
    private final String className;

    /**
     *  Подставить ключевик в URL (например, тикер) и отдать готовый URL.
     *
     * @param key
     * @return
     */
    public String prepareUrl(String key){
        return String.format(url, key);
    }
}
