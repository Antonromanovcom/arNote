package com.antonromanov.arnote.model.investing.response;

import com.antonromanov.arnote.model.investing.response.xmlpart.BoardsColumns;
import com.antonromanov.arnote.model.investing.response.xmlpart.DataBlock;
import com.antonromanov.arnote.model.investing.response.xmlpart.SecuritiesColumns;
import com.antonromanov.arnote.model.investing.response.xmlpart.boardid.MoexDocumentForBoardIdRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexDetailInfoRs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
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
                    .securitiesColumns(EnumSet.of(SecuritiesColumns.SECID, SecuritiesColumns.PREVADMITTEDQUOTE))
                    .build(),
            MoexDocumentRs.class),
    GET_INSTRUMENT_DETAIL_INFO( "/engines/stock/markets/shares/securities/{p1:[a-z]{1,5}}",
            UrlRequestParams.builder().issMeta(false).build(),
            MoexDetailInfoRs.class),
    GET_BOARD_ID( "/securities/{p1:[a-z]{1,5}}.xml",
            UrlRequestParams.builder()
                    .issMeta(false)
                    .issOnly(EnumSet.of(DataBlock.BOARDS))
                    .boardsColumns(EnumSet.of(BoardsColumns.SECID, BoardsColumns.BOARDID, BoardsColumns.IS_PRIMARY))
                    .build(),
            MoexDocumentForBoardIdRs.class),
    GET_INSTRUMENT_NAME( "/engines/stock/markets/shares/boards/{p2:[a-z]{1,5}}/securities.xml",
            UrlRequestParams.builder()
                    .issMeta(false)
                    .issDp("comma")
                    .issOnly(EnumSet.of(DataBlock.SECURITIES))
                    .securitiesColumns(EnumSet.of(SecuritiesColumns.SECID, SecuritiesColumns.SECNAME))
                    .build(),
            MoexDocumentRs.class),
    GET_DELTA( "/history/engines/stock/markets/shares/boards/{p2:[a-z]{1,5}}/securities/{p1:[a-z]{1,5}}/candles.xml",
            UrlRequestParams.builder()
                    .issMeta(false)
                    .from("2000-01-01")
                    .build(),
            MoexDocumentRs.class);


    private final String url;
    private final UrlRequestParams requestParams; // количество параметризированных кусков пути
    private final Class<?> className;


    /**
     *  Поиск кол-ва параметров в строке
     *
     * @param
     * @return
     */
    public int calculateStringParametersCount(){
        return StringUtils.countMatches(url, "/{p");
    }
}
