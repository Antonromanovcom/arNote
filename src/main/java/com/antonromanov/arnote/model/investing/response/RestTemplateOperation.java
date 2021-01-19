package com.antonromanov.arnote.model.investing.response;

import com.antonromanov.arnote.model.investing.response.xmlpart.boardid.MoexDocumentForBoardIdRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexDetailInfoRs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;


@Getter
@AllArgsConstructor
public enum RestTemplateOperation {
    GET_DIVS_MOEX(StockExchange.MOEX, "/securities/%s/dividends.xml?iss.meta=off", null), //todo: переиспользовать класс URL
    GET_LAST_QUOTE_MOEX(StockExchange.MOEX, "/engines/stock/markets/shares/boards/%s/securities.xml?" +
            "iss.dp=comma&iss.meta=off&iss.only=securities&securities.columns=SECID,PREVADMITTEDQUOTE", MoexDocumentRs.class),
    GET_INSTRUMENT_DETAIL_INFO(StockExchange.MOEX, "/engines/stock/markets/shares/securities/%s?iss.meta=off", MoexDetailInfoRs.class),
    GET_BOARD_ID(StockExchange.MOEX, "/securities/%s.xml?iss.meta=off&iss.only=boards&boards.columns=secid,boardid,is_primary",
            MoexDocumentForBoardIdRs.class),
    GET_INSTRUMENT_NAME(StockExchange.MOEX, "/engines/stock/markets/shares/boards/tqbr/securities.xml?" +
            "iss.meta=off&iss.only=securities&securities.columns=SECID,SECNAME", MoexDocumentRs.class),
    GET_DELTA(StockExchange.MOEX, "/history/engines/stock/markets/shares/boards/%s/securities/%s/candles.xml?" +
            "from=2000-01-01?iss.meta=off", MoexDocumentRs.class);


    private final StockExchange stockExchange;
    private final String url;
    private final Class<?> className;


    /**
     *  Подставить ключевик в URL (например, тикер) и отдать готовый URL.
     *
     * @param key
     * @return
     */
    public String prepareUrl(String host, List<String> key){
        return key.size()==1 ? host + String.format(url, key.get(0)) : host + String.format(url, key.get(0), key.get(1)); //todo: переделать через URL
    }
}
