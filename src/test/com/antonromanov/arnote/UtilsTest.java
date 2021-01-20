package com.antonromanov.arnote;

import com.antonromanov.arnote.model.investing.cache.CurrentQuoteCached;
import com.antonromanov.arnote.model.investing.response.enums.RestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDataRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.service.investment.cache.CacheService;
import com.antonromanov.arnote.service.investment.http.client.ArNoteHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static com.antonromanov.arnote.utils.Utils.prepareUrl;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilsTest {



    @Autowired
    private ArNoteHttpClient client;

    @Autowired
    private CacheService cacheService;

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
        MoexDocumentRs doc = new MoexDocumentRs();
        MoexDataRs data = new MoexDataRs();
        MoexRowsRs row = new MoexRowsRs();
        row.setSecid("SBER");
        row.setPrevAdmittedQuote("111");
        ArrayList<MoexRowsRs> rowsList = new ArrayList<>();
        rowsList.add(row);
        data.setRow(rowsList);
        doc.setData(data);

        CurrentQuoteCached recById1 = cacheService.justGetById("TQBR");
        CurrentQuoteCached rec = cacheService.createOrUpdateRecord("TQBR", doc);
        CurrentQuoteCached recById2 = cacheService.justGetById("TQBR");
        CurrentQuoteCached rec1 = cacheService.getOrCreateLastQuote("TQBR", doc);
        CurrentQuoteCached rec2 = cacheService.getOrCreateLastQuote("TABR", doc);
        CurrentQuoteCached rec3 = cacheService.getOrCreateLastQuote("TABR", doc);

        assertEquals(1, rec1.getCount());
        assertEquals(2, rec3.getCount());

    }
}
