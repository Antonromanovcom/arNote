package com.antonromanov.arnote.service.investment.xmlparse;

import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.MoexDocumentRs;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

public interface XmlHandler {
    Optional<ConsolidatedDividendsRs> parse (ResponseEntity<String> response);
    Optional<MoexDocumentRs> marshall (ResponseEntity<String> response);


}
