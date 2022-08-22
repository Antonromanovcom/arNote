package com.antonromanov.arnote.domain.investing.dto.response.xmlpart.currentquote;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "document")
public class MoexStockCurrentPriceRs {
        @XmlElement(name = "data")
        MoexDocumentRs moexDocumentRsObject;
}
