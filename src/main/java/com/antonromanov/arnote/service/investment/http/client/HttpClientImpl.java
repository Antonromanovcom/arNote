package com.antonromanov.arnote.service.investment.http.client;

import com.antonromanov.arnote.exceptions.MoexRequestException;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.RestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.xmlpart.common.CommonMoexDoc;
import com.antonromanov.arnote.service.investment.xmlparse.XmlHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.Optional;

@Service
public class HttpClientImpl implements HttpClient {

    /**
     * url на получение токена
     */
    @Value("${moexUrl}")
    public String MOEX_URL;

    private final XmlHandler xmlParser;
    private final RestTemplate rt;

    public HttpClientImpl(XmlHandler xmlParser, RestTemplate rt) {
        this.xmlParser = xmlParser;
        this.rt = rt;
    }


    /**
     * Запросить дивиденды.
     *
     * @param ticker
     * @return
     */
    @Override
    public Optional<ConsolidatedDividendsRs> sendAndParse(String ticker) {
        try {
            return xmlParser.parse(rt.getForEntity("http://iss.moex.com/iss/securities/"
                    + ticker
                    + "/dividends.xml?iss.meta=off", String.class));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Запросить последнюю ставку.
     *
     * @param type
     * @return
     */
    @Override
    public CommonMoexDoc sendAndMarshall(RestTemplateOperation type, String ticker) {
        if (type!=RestTemplateOperation.GET_DIVS_MOEX) {
            try {
                return xmlParser.marshall(rt.getForEntity(type.prepareUrl(MOEX_URL, Arrays.asList(ticker)), String.class), type.getClassName());
            } catch (Exception e) {
                throw new MoexRequestException();
            }
        } else {
            throw new MoexRequestException();
        }
    }

    @Override
    public CommonMoexDoc sendAndMarshall2(RestTemplateOperation type, String boardId, String ticker) {
        if (type!=RestTemplateOperation.GET_DIVS_MOEX) {
            try {
                return xmlParser.marshall(rt.getForEntity(type.prepareUrl(MOEX_URL, Arrays.asList(boardId, ticker)), String.class), type.getClassName());
            } catch (Exception e) {
                throw new MoexRequestException();
            }
        } else {
            throw new MoexRequestException();
        }
    }
}
