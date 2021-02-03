package com.antonromanov.arnote.model.investing.response.enums;

import com.antonromanov.arnote.model.investing.response.UrlRequestParams;
import com.antonromanov.arnote.model.investing.response.xmlpart.boardid.MoexDocumentForBoardIdRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.enums.BoardsColumns;
import com.antonromanov.arnote.model.investing.response.xmlpart.enums.DataBlock;
import com.antonromanov.arnote.model.investing.response.xmlpart.enums.MarketData;
import com.antonromanov.arnote.model.investing.response.xmlpart.enums.SecuritiesColumns;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexDetailInfoRs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.EnumSet;


@Getter
@AllArgsConstructor
public enum RestTemplateOperation {


    GET_DIVS_MOEX("/securities/{p1:[a-z]{1,5}}/dividends.xml", UrlRequestParams.builder().issMeta(false).build(), null),
    GET_LAST_QUOTE_MOEX("/engines/stock/markets/shares/boards/{p2:[a-z]{1,5}}/securities.xml",
            UrlRequestParams.builder()
                    .issMeta(false)
                    .issDp("comma")
                    .issOnly(EnumSet.of(DataBlock.SECURITIES))
                    .securitiesColumns(EnumSet.of(SecuritiesColumns.SECID, SecuritiesColumns.PREVADMITTEDQUOTE, SecuritiesColumns.COUPONPERIOD))
                    .build(),
            MoexDocumentRs.class),
    GET_INSTRUMENT_DETAIL_INFO("/engines/stock/markets/shares/securities/{p1:[a-z]{1,5}}",
            UrlRequestParams.builder().issMeta(false).build(),
            MoexDetailInfoRs.class),
    GET_BOARD_ID("/securities/{p1:[a-z]{1,5}}.xml",
            UrlRequestParams.builder()
                    .issMeta(false)
                    .issOnly(EnumSet.of(DataBlock.BOARDS))
                    .boardsColumns(EnumSet.of(BoardsColumns.SECID, BoardsColumns.BOARDID, BoardsColumns.IS_PRIMARY))
                    .build(),
            MoexDocumentForBoardIdRs.class),
    GET_INSTRUMENT_NAME("/engines/stock/markets/shares/boards/{p2:[a-z]{1,5}}/securities.xml",
            UrlRequestParams.builder()
                    .issMeta(false)
                    .issDp("comma")
                    .issOnly(EnumSet.of(DataBlock.SECURITIES))
                    .securitiesColumns(EnumSet.of(SecuritiesColumns.SECID, SecuritiesColumns.SECNAME))
                    .build(),
            MoexDocumentRs.class),
    GET_DELTA("/history/engines/stock/markets/shares/boards/{p2:[a-z]{1,5}}/securities/{p1:[a-z]{1,5}}/candles.xml",
            UrlRequestParams.builder()
                    .issMeta(false)
                    .from("2000-01-01")
                    .build(),
            MoexDocumentRs.class),
    GET_BONDS("/engines/stock/markets/bonds/boardgroups/{p1:[a-z]{1,5}}/securities.xml",
            UrlRequestParams.builder()
                    .issMeta(false)
                    .issDp("comma")
                    .issOnly(EnumSet.of(DataBlock.SECURITIES))
                    .securitiesColumns(EnumSet.of(SecuritiesColumns.SECID, SecuritiesColumns.SECNAME, SecuritiesColumns.PREVLEGALCLOSEPRICE,
                            SecuritiesColumns.COUPONVALUE, SecuritiesColumns.COUPONPERCENT, SecuritiesColumns.LOTVALUE,
                            SecuritiesColumns.COUPONPERIOD, SecuritiesColumns.CURRENCYID, SecuritiesColumns.LOTSIZE))
                    .build(),
            MoexDocumentRs.class),
    GET_CURRENCY_CHANGE_COURSES("/statistics/engines/futures/markets/indicativerates/securities",
            UrlRequestParams.builder()
                    .issMeta(false)
                    .issOnly(EnumSet.of(DataBlock.SECURITIES))
                    .build(),
            MoexDocumentRs.class),
     GET_ALL_SHARES("/engines/stock/markets/shares/boards/{p2:[a-z]{1,5}}/securities.xml",
            UrlRequestParams.builder()
                    .issMeta(false)
                    .issOnly(EnumSet.of(DataBlock.SECURITIES))
                    .build(),
            MoexDocumentRs.class),
    GET_TRADE_MODES("/engines/stock/markets/shares/boards.xml",
            UrlRequestParams.builder()
                    .issMeta(false)
                    .build(),
            MoexDocumentRs.class),
    GET_15_MINUTE_PRICE_UPDATE("/engines/stock/markets/shares/securities/{p1:[a-z]{1,5}}.xml",
            UrlRequestParams.builder()
                    .issMeta(false)
                    .issOnly(EnumSet.of(DataBlock.MARKETDATA))
                    .marketDataColumns(EnumSet.of(MarketData.SECID, MarketData.BOARDID, MarketData.LAST, MarketData.UPDATETIME,
                            MarketData.LASTCHANGE, MarketData.LASTCHANGEPRCNT))
                    .build(),
            MoexDocumentRs.class);

    private final String url;
    private final UrlRequestParams requestParams;
    private final Class<?> className;

}
