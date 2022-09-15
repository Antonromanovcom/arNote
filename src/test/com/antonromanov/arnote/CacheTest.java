package com.antonromanov.arnote;

import com.antonromanov.arnote.domain.investing.dto.cache.enums.CacheDictType;
import com.antonromanov.arnote.domain.investing.service.cache.CacheService;
import com.antonromanov.arnote.domain.investing.service.calc.shares.SharesCalcService;
import com.antonromanov.arnote.domain.investing.service.requestservice.RequestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;


/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class CacheTest {
/*
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
        calcService.getDivsByTicker("SBER");
        calcService.getDivsByTicker("SBER");
        assertEquals(1, httpClient.getCounter());
    }*/


    /**
     * Тестируем работоспособность нового кэша на случай, если он не заполнен.
     */
   /* @Test
    public void emptyCacheTest() {
        Boolean b = cacheService.checkDict(CacheDictType.BOARD_ID_BY_TICKER, "CURRENT_QUOTE");
        assertFalse(b);
    }*/
}
