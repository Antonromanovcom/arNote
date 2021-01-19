package com.antonromanov.arnote.model.investing.response.xmlpart.currentquote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "row")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoexRowsRs {

        @XmlAttribute(name = "SECID")
        private String secid;

        @XmlAttribute(name = "PREVADMITTEDQUOTE")
        private String prevAdmittedQuote;

        @XmlAttribute(name = "SECNAME")
        private String secName; // название бумаги

        @XmlAttribute(name = "TRADEDATE")
        private String tradeDate; // дата торговли

        @XmlAttribute(name = "LEGALCLOSEPRICE")
        private String legalClosePrice; // ставка закрытия
}
