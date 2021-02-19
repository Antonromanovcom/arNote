package com.antonromanov.arnote.service.investment.calc.shares.foreign;

import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.Purchase;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.CurrentPriceRs;
import com.antonromanov.arnote.model.investing.response.DeltaRs;
import com.antonromanov.arnote.model.investing.response.foreignstocks.AlphavantageSearchListRs;
import com.antonromanov.arnote.model.investing.response.foreignstocks.AlphavantageSearchRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDataRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo.MoexDetailInfoRs;
import com.antonromanov.arnote.service.investment.calc.shares.SharesCalcService;
import com.antonromanov.arnote.service.investment.requestservice.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Имплементация расчетного сервиса для работы с иностранными бумагами.
 *
 */
public class ForeignCalcServiceImpl implements SharesCalcService {

    @Autowired
    private RequestService httpClient;

    @Override
    public Optional<ConsolidatedDividendsRs> getDivsByTicker(ArNoteUser user, String ticker) {
        return Optional.empty();
    }

    @Override
    public Optional<Double> getCurrentQuoteByTicker(String ticker) {
        return Optional.empty();
    }

    /**
     * Запросить текущую цену акции по тикеру. Та, что обновляется раз в 15 минут с торгов.
     *
     * @return
     */
    @Override
    public CurrentPriceRs getRealTimeQuote(String ticker) {
        AlphavantageSearchListRs response = httpClient.sendAndMarshallForeignRequest(ticker);
        List<AlphavantageSearchRs> filteredList = response.getBestMatches().stream()
                .filter(sec-> "Equity".equalsIgnoreCase(sec.getType()))
                .collect(Collectors.toList());
        MoexDocumentRs document = new MoexDocumentRs();
        MoexDataRs documentData = new MoexDataRs();
        ArrayList<MoexRowsRs> rows = filteredList.stream()
                .map(r->{
                    MoexRowsRs row = new MoexRowsRs();
                    row.setSecid(r.getSymbol());
                    row.setCurrencyId(r.getCurrency());
                    row.setSecName(r.getName());
                    return row;
                })
                .collect(Collectors.toCollection(ArrayList::new));

        documentData.setRow(rows);
        document.setData(documentData);
       // return document;
        return null;
    }

    @Override
    public Optional<MoexDocumentRs> getCurrentQuoteByBoardId(String boardId) {
        return Optional.empty();
    }

    @Override
    public Optional<MoexDetailInfoRs> getDetailInfo(ArNoteUser user, String ticker) {
        return Optional.empty();
    }

    @Override
    public String getBoardId(String ticker) {
        return null;
    }

    @Override
    public Optional<String> getInstrumentName(String boardId, String ticker) {
        return Optional.empty();
    }

    @Override
    public DeltaRs calculateDelta(String boardId, String ticker, Double currentStockPrice, List<Purchase> purchaseList) {
        return null;
    }

    @Override
    public Integer calculateFinalPrice(Bond bond, ArNoteUser user) {
        return null;
    }

    @Override
    public ConsolidatedDividendsRs getDividends(Bond bond, ArNoteUser user) {
        return null;
    }

    @Override
    public String getCurrencyOfShareFromDetailInfo(String ticker, ArNoteUser user) {
        return null;
    }

    @Override
    public Integer getMinimalLot(String ticker, ArNoteUser user) {
        return null;
    }

    @Override
    public MoexDocumentRs getHistory(String ticker, String boardId) {
        return null;
    }

    @Override
    public Double getCurrencyMultiplier(String currency) {
        return null;
    }

    @Override
    public MoexDocumentRs findSharesByBoardId(String boardId) {
        return null;
    }

    @Override
    public List<String> getTradeModes() {
        return null;
    }

    @Override
    public MoexDocumentRs getForeignInstrumentsByName(String keyword) {
        return null;
    }
}
