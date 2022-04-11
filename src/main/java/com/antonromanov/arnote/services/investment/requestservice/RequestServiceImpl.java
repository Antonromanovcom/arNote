package com.antonromanov.arnote.services.investment.requestservice;

import com.antonromanov.arnote.exceptions.MoexRequestException;
import com.antonromanov.arnote.model.investing.external.requests.ForeignRequests;
import com.antonromanov.arnote.model.investing.external.requests.MoexRestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.common.CommonMoexDoc;
import com.antonromanov.arnote.services.investment.xmlparse.XmlHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.antonromanov.arnote.utils.ArNoteUtils.*;

@Service
@Slf4j
public class RequestServiceImpl implements RequestService {

    /**
     * url на получение токена
     */
    @Value("${moexUrl}")
    public String MOEX_URL;

    private final XmlHandler xmlParser;
    private final RestTemplate rt;
    private final ObjectMapper objectMapper;
    private Integer counter = 0;

    public RequestServiceImpl(XmlHandler xmlParser, RestTemplate rt, ObjectMapper objectMapper) {
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
    public CommonMoexDoc sendAndMarshall(MoexRestTemplateOperation type, String ticker, String boardId) {
        if (type != MoexRestTemplateOperation.GET_DIVS_MOEX) {
            try {
                log.info("Sending MOEX request to: {}",
                        prepareUrl(MOEX_URL, type, serializeObjectToMVMap(type.getRequestParams().convertByAdapter()),
                                prepareParametersMap(ticker, boardId)));

                return xmlParser.marshall(rt.getForEntity(prepareUrl(MOEX_URL,
                        type,
                        serializeObjectToMVMap(type.getRequestParams().convertByAdapter()),
                        prepareParametersMap(ticker, boardId)),
                        String.class),
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
    public CommonMoexDoc getHistory(MoexRestTemplateOperation type, String ticker, String boardId, String dateFrom, String dateTill, int start) {
        try {
            log.info("Sending MOEX request for history. Url: {}",
                    prepareUrlForHistory(MOEX_URL, type, serializeObjectToMVMap(type.getRequestParams().convertByAdapter()),
                            prepareParametersMap(ticker, boardId), dateFrom, dateTill, start));

            return xmlParser.marshall(
                    rt.getForEntity(prepareUrlForHistory(MOEX_URL, type, serializeObjectToMVMap(type.getRequestParams().convertByAdapter()),
                            prepareParametersMap(ticker, boardId), dateFrom, dateTill, start), String.class),
                    type.getClassName());
        } catch (Exception e) {
            throw new MoexRequestException();
        }
    }

    @Override
    public CommonMoexDoc getCandles(MoexRestTemplateOperation type, String ticker, String dateFrom, String dateTill, int start) {
        try {

          String url = prepareUrlForCandles(MOEX_URL, type, serializeObjectToMVMap(type.getRequestParams().convertByAdapter()),
                  prepareParametersMap(ticker, null), dateFrom, dateTill, start); // todo: подумать как сделать один метод и для истории и для свечей
            log.info("Sending MOEX request for candles. Url: {}", url);


            return xmlParser.marshall(
                    rt.getForEntity(url, String.class),
                    type.getClassName());
        } catch (Exception e) {
            throw new MoexRequestException();
        }
    }

    /**
     * Подготовить мапу параметров.
     *
     * @param param1 - для MOEX как правило тикер.
     * @param param2 - для MOEX как правило boardId.
     * @return
     */
    private Map<String, String> prepareParametersMap(String param1, String param2) { // todo: переделать чтобы разное кол-во параметров передавать можно было
        Map<String, String> m = new HashMap<>();
        m.put("p1", param1);
        m.put("p2", param2);
        return m;
    }

    /**
     * Подготовить  мапу нумерованных параметров для URLа
     *
     * @return
     */
    private Map<String, String> prepareParametersMapFromList(LinkedList<String> paramList) {
        List<Integer> indexList = new LinkedList<>();
        for (int i = 0; i < paramList.size(); i++) {
            indexList.add(i);
        }
        return indexList.stream().collect(Collectors.toMap(i -> "p"+(i+1), paramList::get));
    }

    /**
     * Сериализовать параметры запроса в MultiValueMap.
     *
     * @param type
     * @return
     */
    @Override
    public MultiValueMap<String, String> serializeObjectToMVMap(Object type) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Map<String, String> maps = objectMapper.convertValue(type, new TypeReference<Map<String, String>>() {
        });
        parameters.setAll(maps);
        return parameters;
    }

    /**
     * Нужно исключительно для тестирования.
     *
     * @return
     */
    @Override
    public int getCounter() {
        return counter;
    }

    /**
     * Отправить запрос в буржуйское API.
     *
     * @param <T>         - респонс.
     * @param requestType - тип, содержащие разные данные по урлу и прочему.
     * @param ticker      - тикер бумаги.
     * @param clazz       - класс респонса.
     * @return
     */
    @Override
    public <T> T sendAndMarshallForeignRequest(ForeignRequests requestType, LinkedList<String> ticker, Class<T> clazz) {

        String url = prepareForeignUrl(requestType,
                serializeObjectToMVMap(requestType.getRequestParams()), prepareParametersMapFromList(ticker));
        log.info("Sending foreign request to: {}", url);
        try {
            ResponseEntity<String> response = rt.getForEntity(url, String.class);
            return objectMapper.readValue(response.getBody(), clazz);
        } catch (Exception e) {
            log.error("Произошла ошибка запроса данных из API или ошибка парсинга результата по запросу: {}. Ошибка: {}", requestType, e.getMessage());
            return null;
        }
    }

    /**
     * Отправить запрос в буржуйское API без сериализации (вернуть сразу респонс назад).
     *
     *
     * @param requestType - тип, содержащие разные данные по урлу и прочему.
     * @param ticker - тикер бумаги.
     *
     * @return
     */
    @Override
    public Optional<String> sendForeignRequest(ForeignRequests requestType, LinkedList<String> ticker) {
        String url = prepareForeignUrl(requestType,
                serializeObjectToMVMap(requestType.getRequestParams()), prepareParametersMapFromList(ticker));
        log.info("Sending foreign request to: {}", url);
        try {
            ResponseEntity<String> response = rt.getForEntity(url, String.class);
            return Optional.ofNullable(response.getBody());
        } catch (Exception e){
            return Optional.empty();
        }
    }
}
