package com.antonromanov.arnote;

import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.external.requests.MoexRestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.enums.StockExchange;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.services.investment.calc.CommonService;
import com.antonromanov.arnote.services.investment.requestservice.RequestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashMap;
import java.util.Map;
import static com.antonromanov.arnote.sex.utils.ArNoteUtils.prepareUrl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ArNoteUtilsTest {

    @Autowired
    private RequestService client;

    @Autowired
    UsersRepo repo;

    @Autowired
    BondsRepo bondsRepo;

    @Value("${moexUrl}")
    public String MOEX_URL;

    @Autowired
    private CommonService commonService;


    @Test
    public void testCalcFactory() {
        Bond b = new Bond();
        b.setStockExchange(StockExchange.MOEX);
        assertNull(commonService.prepareCurrentPrice(b));
    }

    @Test
    public void getUrlTest() {

        Map<String, String> m = new HashMap<>();
        m.put("p1", "1");
        m.put("p2", "2");

        String url = prepareUrl(MOEX_URL,
                MoexRestTemplateOperation.GET_DIVS_MOEX,
                client.serializeObjectToMVMap(MoexRestTemplateOperation.GET_LAST_QUOTE_MOEX.getRequestParams().convertByAdapter()), m);

        String urlToCheck = url.substring("http://".length() + MOEX_URL.length());


        assertEquals("/securities/1/dividends.xml?iss.meta=off&iss.dp=comma&iss." +
                "only=securities&securities.columns=SECID,PREVADMITTEDQUOTE,COUPONPERIOD", urlToCheck);

    }
}
