package com.antonromanov.arnote.service.investment.cache;

import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
@Slf4j
@Data
public class CacheService2 {

    /**
     * Кэш с последними ставками;
     */
    private HashMap<String, MoexDocumentRs> lastQuoteCache;




}
