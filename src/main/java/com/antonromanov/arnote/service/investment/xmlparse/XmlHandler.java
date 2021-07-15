package com.antonromanov.arnote.service.investment.xmlparse;

import com.antonromanov.arnote.domain.investing.dto.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.common.CommonMoexDoc;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

public interface XmlHandler {
    Optional<ConsolidatedDividendsRs> parse (ResponseEntity<String> response);
    CommonMoexDoc marshall(ResponseEntity<String> response, Class<?> moexClass);
}
