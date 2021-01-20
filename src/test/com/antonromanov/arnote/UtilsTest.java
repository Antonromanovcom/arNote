package com.antonromanov.arnote;


import com.antonromanov.arnote.model.investing.response.RestTemplateOperation;
import com.antonromanov.arnote.service.investment.HighloadService;
import com.antonromanov.arnote.service.investment.Record;
import com.antonromanov.arnote.service.investment.http.client.ArNoteHttpClient;
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

    @Autowired
    private HighloadService highloadService;

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

    @Test
    public void getCache() throws InterruptedException {

        Record rec = highloadService.createOrUpdateRecord(1);
        Record r1 = highloadService.getOrCreateRecord(rec.getId());
        System.out.println("Достали из кэша  запись с id = " + r1.getId() + " и counter " + r1.getCount());
        Record r2 = highloadService.getOrCreateRecord(rec.getId());
        System.out.println("Достали из кэша  второй раз запись с id = " + r2.getId() + " и counter " + r2.getCount());
Thread.sleep(9000);
        Record r3 = highloadService.getOrCreateRecord(rec.getId());
        System.out.println("Достали из кэша  третий раз запись с id = " + r3.getId() + " и counter " + r3.getCount());

        assertEquals(1, r2.getId());

    }
}
