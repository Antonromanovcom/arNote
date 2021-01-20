package com.antonromanov.arnote.model.investing.response;

import com.antonromanov.arnote.model.investing.response.xmlpart.enums.BoardsColumns;
import com.antonromanov.arnote.model.investing.response.xmlpart.enums.DataBlock;
import com.antonromanov.arnote.model.investing.response.xmlpart.enums.SecuritiesColumns;
import com.antonromanov.arnote.model.investing.response.xmlpart.UrlQueryParameters;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.EnumSet;
import java.util.stream.Collectors;

/**
 * Параметры URL (которые после ? идут)
 */
@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlRequestParams {
    private Boolean issMeta;
    private String issDp;
    private EnumSet<DataBlock> issOnly;
    private EnumSet<SecuritiesColumns> securitiesColumns;
    private EnumSet<BoardsColumns> boardsColumns;
    private String from;

    public UrlRequestParamsAdapter convertByAdapter(){
        return UrlRequestParamsAdapter
                .builder()
                .issMeta(this.issMeta)
                .issDp(this.issDp)
                .from(this.from)
                .issOnly(convertEnumSet(this.issOnly, (UrlQueryParameters::getCode)))
                .securitiesColumns(convertEnumSet(this.securitiesColumns, (UrlQueryParameters::getCode)))
                .boardsColumns(convertEnumSet(this.boardsColumns, (UrlQueryParameters::getCode)))
                .build();
    }

    interface Expression{
        String castIt(UrlQueryParameters n);
    }


    private <T extends Enum<T>>String  convertEnumSet(EnumSet<T> set, Expression func) {
        return set==null? null : set.stream()
                .map((T n) -> func.castIt((UrlQueryParameters) n))
                .collect(Collectors.joining(","));
    }


 //   DataBlock.SECURITIES.getCode()

}
