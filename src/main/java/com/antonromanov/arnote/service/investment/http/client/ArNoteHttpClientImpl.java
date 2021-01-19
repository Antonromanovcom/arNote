package com.antonromanov.arnote.service.investment.http.client;

import com.antonromanov.arnote.exceptions.MoexRequestException;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.RestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.xmlpart.common.CommonMoexDoc;
import com.antonromanov.arnote.service.investment.xmlparse.XmlHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static com.antonromanov.arnote.utils.Utils.prepareUrl;

@Service
public class ArNoteHttpClientImpl implements ArNoteHttpClient {

    /**
     * url на получение токена
     */
    @Value("${moexUrl}")
    public String MOEX_URL;

    private final XmlHandler xmlParser;
    private final RestTemplate rt;
    private ObjectMapper objectMapper;

    public ArNoteHttpClientImpl(XmlHandler xmlParser, RestTemplate rt, ObjectMapper objectMapper) {
        this.xmlParser = xmlParser;
        this.rt = rt;
        this.objectMapper = objectMapper;
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
     * Сделать запрос и смаршелить результат.
     *
     * @param type
     * @return
     */
    @Override
    public CommonMoexDoc sendAndMarshall(RestTemplateOperation type, String ticker, String boardId) {
        if (type!=RestTemplateOperation.GET_DIVS_MOEX) {
            try {

                Map<String, String> m = new HashMap<>();
                m.put("p1", ticker);
                m.put("p2", boardId);

                return xmlParser.marshall(rt.getForEntity(prepareUrl(MOEX_URL, type, serializeObjectToMVMap(type), m), String.class),
                        type.getClassName());
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
               // return xmlParser.marshall(rt.getForEntity(type.prepareUrl(MOEX_URL, Arrays.asList(boardId, ticker)), String.class), type.getClassName());
                return null;
            } catch (Exception e) {
                throw new MoexRequestException();
            }
        } else {
            throw new MoexRequestException();
        }
    }

    /**
     * Сериализовать параметры запроса в MultiValueMap.
     *
     * @param type
     * @return
     */
    @Override
    public MultiValueMap<String, String> serializeObjectToMVMap(RestTemplateOperation type) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Map<String, String> maps = objectMapper.convertValue(type.getRequestParams().convertByAdapter(),
                new TypeReference<Map<String, String>>() {});
        parameters.setAll(maps);
        return parameters;
    }
}
