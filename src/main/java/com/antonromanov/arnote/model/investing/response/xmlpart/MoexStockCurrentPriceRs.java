package com.antonromanov.arnote.model.investing.response.xmlpart;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "document")
public class MoexStockCurrentPriceRs {
        @XmlElement(name = "data")
        MoexDocumentRs moexDocumentRsObject;
}
