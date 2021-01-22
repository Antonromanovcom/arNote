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

        @XmlAttribute(name = "PREVLEGALCLOSEPRICE")
        private String prevLegalClosePrice; // Официальная цена закрытия предыдущего дня

        @XmlAttribute(name = "COUPONVALUE")
        private String couponValue; // Сумма купона, в валюте номинала

        @XmlAttribute(name = "COUPONPERCENT")
        private String couponPercent; // Ставка купона, %

        @XmlAttribute(name = "LOTVALUE")
        private String lotValue; // Номинальная стоимость лота, в валюте номинала

        @XmlAttribute(name = "COUPONPERIOD")
        private String couponPeriod; // Длительность купона

        @XmlAttribute(name = "CURRENCYID")
        private String currencyId; // Длительность купона

}
