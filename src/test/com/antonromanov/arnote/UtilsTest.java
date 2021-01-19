package com.antonromanov.arnote;


import com.antonromanov.arnote.model.investing.response.RestTemplateOperation;
import com.antonromanov.arnote.service.investment.calc.CalculateService;
import com.antonromanov.arnote.service.investment.http.client.ArNoteHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import static com.antonromanov.arnote.utils.Utils.prepareUrl;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilsTest {


    @Autowired
    private ArNoteHttpClient client;

    @Value("${moexUrl}")
    public String MOEX_URL;

    @Test
    public void getUrlTest() {

        Map<String, String> m = new HashMap<>();
        m.put("p1", "1");
        m.put("p2", "2");

        String url = prepareUrl(MOEX_URL, RestTemplateOperation.GET_DIVS_MOEX, client.serializeObjectToMVMap(RestTemplateOperation.GET_LAST_QUOTE_MOEX), m);
        String urlToCheck = url.substring("http://".length() + MOEX_URL.length());


        assertEquals("/securities/1/dividends.xml?iss.meta=on&iss.dp=comma&iss." +
                "only=securities&securities.columns=SECID,PREVADMITTEDQUOTE", urlToCheck);

    }
}
