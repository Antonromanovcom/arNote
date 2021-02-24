package com.antonromanov.arnote;

import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.repositoty.UsersRepo;
import com.antonromanov.arnote.service.investment.cache.CacheService;
import com.antonromanov.arnote.service.investment.calc.shares.SharesCalcService;
import com.antonromanov.arnote.service.investment.requestservice.RequestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {

    @Autowired
    private RequestService client;

    @Autowired
    UsersRepo repo;

    @Autowired
    BondsRepo bondsRepo;

    @Value("${moexUrl}")
    public String MOEX_URL;

    @Autowired
    private RequestService httpClient;

    @Autowired
    @Qualifier("moexCalculateServiceImpl")
    private SharesCalcService calcService;

    @Autowired
    private CacheService cacheService;


    @Test
    public void getCache() {
        calcService.getCurrentQuoteByBoardId("TQBR");
        calcService.getCurrentQuoteByBoardId("TQBR");
        assertEquals(1, httpClient.getCounter());
        calcService.getCurrentQuoteByBoardId("TQBS");
        assertEquals(2, httpClient.getCounter());
    }

    @Test
    public void getCachedDivsByTicker() {
        assertEquals(0, httpClient.getCounter());
        calcService.getDivsByTicker(repo.findById(1L).get(),"SBER");
        calcService.getDivsByTicker(repo.findById(1L).get(),"SBER");
        assertEquals(1, httpClient.getCounter());
    }

    /**
     * Тестируем работоспособность нового кэша.
     */
    @Test
    public void newCacheTest() {
        MoexDocumentRs doc = (MoexDocumentRs) calcService.getCurrentQuoteByBoardId("TQBR").map(e->{
            cacheService.putToCache("GET_CURRENT_QUOTE_MOEX", "CURRENT_QUOTE", e, MoexDocumentRs.class);
            return cacheService.getDict("GET_CURRENT_QUOTE_MOEX", "CURRENT_QUOTE");
        }).orElse(null);
        assertNotNull(doc);
    }
}
