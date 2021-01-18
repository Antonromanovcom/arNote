package com.antonromanov.arnote.service.investment.http.client;

import com.antonromanov.arnote.exceptions.GettingStockExchangeDataException;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.RestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.xmlpart.CommonMoexDoc;
import com.antonromanov.arnote.model.investing.response.xmlpart.MoexDocumentRs;
import com.antonromanov.arnote.service.investment.xmlparse.XmlHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

@Service
public class HttpClientImpl implements HttpClient {

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
    public Optional<MoexDocumentRs> sendAndMarshall(RestTemplateOperation type) {
        if (type!=RestTemplateOperation.GET_DIVS_MOEX) {
            try {
                return xmlParser.marshall(rt.getForEntity(type.getUrl(), String.class));
            } catch (Exception e) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}
