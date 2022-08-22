package com.antonromanov.arnote.domain.investing.dto.response.xmlpart.currentquote;

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

        @XmlAttribute(name = "LOTSIZE")
        private String lotSize; // минимальный размер лота

        @XmlAttribute(name = "COUPONPERIOD")
        private String couponPeriod; // Длительность купона

        @XmlAttribute(name = "CURRENCYID")
        private String currencyId; // Длительность купона

        @XmlAttribute(name = "NEXTCOUPON")
        private String nextCoupon; // Дата выплаты следующего купона

        @XmlAttribute(name = "FACEUNIT")
        private String faceUnit; // Денежная еденица купона



        //============= КУРСЫ ВАЛЮТ ================

        @XmlAttribute(name = "tradedate")
        private String tradeDateForCurrencies;

        @XmlAttribute(name = "tradetime")
        private String tradeTime;

        @XmlAttribute(name = "secid")
        private String currencyExchangeType;

        @XmlAttribute(name = "rate")
        private String rate;

        //============= ТОРГОВЫЕ РЕЖИМЫ ================

        @XmlAttribute(name = "boardid")
        private String boardId;

        @XmlAttribute(name = "title")
        private String title;

        @XmlAttribute(name = "is_traded")
        private String isTraded;

        //========== 15-МИНУТНОЕ ОБНОВЛЕНИЕ ЦЕНЫ ===========

        @XmlAttribute(name = "LAST")
        private String last15MinuteQuote; // последняя ставка

        @XmlAttribute(name = "BOARDID")
        private String tradeMode; // последняя ставка

        @XmlAttribute(name = "UPDATETIME")
        private String updateTime;

        @XmlAttribute(name = "LASTCHANGE")
        private String lastChange; // последнее изменение цены относительно присланного 15 минут назад. Скорее всего в рублях

        @XmlAttribute(name = "LASTCHANGEPRCNT")
        private String lastChangePrcnt; // последнее изменение цены относительно присланного 15 минут назад в процентах

        @XmlAttribute(name = "LCURRENTPRICE")
        private String lCurrentPrice; // используем, если LAST пришел пустой

        //========== СВЕЧИ ===========

        @XmlAttribute(name = "open")
        private String open; // цена открытия

        @XmlAttribute(name = "close")
        private String close; // цена закрытия

        @XmlAttribute(name = "high")
        private String high; // хаи за период

        @XmlAttribute(name = "low")
        private String low; // низы за период

        @XmlAttribute(name = "end")
        private String end; // время/дата завершения торгов




}
