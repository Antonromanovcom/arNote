package com.antonromanov.arnote;

import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.external.requests.ForeignRequests;
import com.antonromanov.arnote.model.investing.external.requests.MoexRestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.BondRs;
import com.antonromanov.arnote.model.investing.response.ConsolidatedInvestmentDataRs;
import com.antonromanov.arnote.model.investing.response.enums.StockExchange;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.investment.calc.CommonService;
import com.antonromanov.arnote.service.investment.requestservice.RequestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static com.antonromanov.arnote.utils.ArNoteUtils.*;
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

    /**
     * Тест правильности составления предикатов для фильтров
     */
    /*@Test
    public void filterPredicateTest() {

        Map<String, String> investingFilterMode = new HashMap<>();
        investingFilterMode.put("BOND_TYPE", "TYPE_SHARE");
        Predicate<BondRs> predicate = complexPredicate(investingFilterMode);

        BondRs b1 = BondRs.builder().type("SHARE").ticker("TICKER1").build();
        BondRs b2 = BondRs.builder().type("SHARE").ticker("TICKER2").build();
        BondRs b3 = BondRs.builder().type("BOND").ticker("TICKER3").build();
        ConsolidatedInvestmentDataRs mockObject = ConsolidatedInvestmentDataRs.builder().bonds(Arrays.asList(b1, b2, b3)).build();
        List<BondRs> mockListFilteredWithOne = mockObject.getBonds().stream().filter(predicate).collect(Collectors.toList());
        assertEquals(2, mockListFilteredWithOne.size());
        investingFilterMode.clear();
        Predicate<BondRs> predicateAfterClear = complexPredicate(investingFilterMode);
        List<BondRs> mockListFilteredWithEmpty = mockObject.getBonds().stream().filter(predicateAfterClear).collect(Collectors.toList());
        assertEquals(3, mockListFilteredWithEmpty.size());
    }*/
}
