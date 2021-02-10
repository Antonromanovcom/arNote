package com.antonromanov.arnote.service.investment.http.client;

import com.antonromanov.arnote.exceptions.MoexRequestException;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.enums.RestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.xmlpart.common.CommonMoexDoc;
import com.antonromanov.arnote.service.investment.xmlparse.XmlHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.antonromanov.arnote.utils.ArNoteUtils.prepareUrl;
import static com.antonromanov.arnote.utils.ArNoteUtils.prepareUrlForHistory;

@Service
@Slf4j
public class ArNoteHttpClientImpl implements ArNoteHttpClient {

    /**
     * url на получение токена
     */
    @Value("${moexUrl}")
    public String MOEX_URL;

    private final XmlHandler xmlParser;
    private final RestTemplate rt;
    private final ObjectMapper objectMapper;
    private Integer counter = 0;

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
            counter = +1;
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
        if (type != RestTemplateOperation.GET_DIVS_MOEX) {
            try {
                counter += 1;
                log.info("Sending request to: {}", prepareUrl(MOEX_URL, type, serializeObjectToMVMap(type), prepareParametersMap(ticker, boardId)));
                return xmlParser.marshall(rt.getForEntity(prepareUrl(MOEX_URL, type, serializeObjectToMVMap(type), prepareParametersMap(ticker, boardId)), String.class),
                        type.getClassName());
            } catch (Exception e) {
                log.error("Ошибка взаимодействия с биржей (маршелинга либо отправки): {}", e.getMessage());
                throw new MoexRequestException();
            }
        } else {
            throw new MoexRequestException();
        }
    }

    @Override
    public CommonMoexDoc getHistory(RestTemplateOperation type, String ticker, String boardId, String dateFrom, String dateTill, int start) {
        try {
            log.info("Sending request for history. Url: {}",
                    prepareUrlForHistory(MOEX_URL, type, serializeObjectToMVMap(type), prepareParametersMap(ticker, boardId), dateFrom, dateTill, start));

            return xmlParser.marshall(
                    rt.getForEntity(prepareUrlForHistory(MOEX_URL, type, serializeObjectToMVMap(type),
                            prepareParametersMap(ticker, boardId), dateFrom, dateTill,  start), String.class),
                    type.getClassName());
        } catch (Exception e) {
            throw new MoexRequestException();
        }
    }

    private Map<String, String> prepareParametersMap(String ticker, String boardId) {
        Map<String, String> m = new HashMap<>();
        m.put("p1", ticker);
        m.put("p2", boardId);
        return m;
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
                new TypeReference<Map<String, String>>() {
                });
        parameters.setAll(maps);
        return parameters;
    }

    @Override
    public int getCounter() {
        return counter;
    }
}
