package com.antonromanov.arnote.service.investment.calc;

import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.RestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.xmlpart.MoexDetailInfoRs;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.service.investment.http.client.HttpClient;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class CalculateServiceImpl implements CalculateService {

    private final HttpClient httpClient;
    private final BondsRepo repo;

    public CalculateServiceImpl(HttpClient httpClient, BondsRepo repo) {
        this.httpClient = httpClient;
        this.repo = repo;
    }


    /**
     * Запросить дивиденды через API биржи, подсчитать сумму проценты относительно текущей цены акции и вернуть все это.
     *
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    @Override
    public Optional<ConsolidatedDividendsRs> getDivsByTicker(LocalUser user, String ticker) {
        if (!isBlank(ticker)) {
            Optional<ConsolidatedDividendsRs> res = httpClient.sendAndParse(ticker);
            if (res.isPresent()) {
                res.get().calculatePercent(repo.findBondByUserAndTicker(user, ticker)
                        .map(Bond::getPrice)
                        .orElse(Double.NaN)); //todo: переделать
                return res;
            }
        }
        return Optional.empty();
    }

    /**
     * Запросить текущую цену (ставку) бумаги.
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    @Override
    public Optional<Double> getCurrentQuote(LocalUser user, String ticker) {
        if (!isBlank(ticker)) {
            return httpClient.sendAndMarshall(RestTemplateOperation.GET_LAST_QUOTE_MOEX)
                    .map(p -> p.getData()
                            .getRow()
                            .stream()
                            .filter(r -> ticker.equals(r.getSecid()))
                            .findFirst()
                            .map(q -> Double.valueOf(q.getPrevAdmittedQuote())))
                    .orElse(null);
        } else{
            return Optional.empty();
        }
    }

    /**
     * Запросить детальную информацию по бумаге (инструменту).
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    @Override
    public Optional<MoexDetailInfoRs> getDetailInfo(LocalUser user, String ticker) {
        if (!isBlank(ticker)) {
            return httpClient.sendAndMarshall(RestTemplateOperation.GET_LAST_QUOTE_MOEX)
                    .map(p -> p.getData()
                            .getRow()
                            .stream()
                            .filter(r -> ticker.equals(r.getSecid()))
                            .findFirst()
                            .map(q -> Double.valueOf(q.getPrevAdmittedQuote())))
                    .orElse(null);
        } else{
            return Optional.empty();
        }
    }
}
