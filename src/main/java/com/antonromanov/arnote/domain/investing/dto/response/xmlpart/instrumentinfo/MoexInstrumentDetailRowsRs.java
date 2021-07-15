package com.antonromanov.arnote.domain.investing.dto.response.xmlpart.instrumentinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "row")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoexInstrumentDetailRowsRs {

        @XmlAttribute(name = "SECID")
        private String secId; // Идентификатор финансового инструмента

        @XmlAttribute(name = "BOARDID")
        private String boardId; // Идентификатор режима торгов

        @XmlAttribute(name = "SHORTNAME")
        private String shortName; // Краткое наименование ценной бумаги

        @XmlAttribute(name = "PREVPRICE")
        private String prevPrice; // Цена последней сделки нормального периода предыдущего торгового дня

        @XmlAttribute(name = "LOTSIZE")
        private String lotSize; // Количество ценных бумаг в одном стандартном лоте

        @XmlAttribute(name = "FACEVALUE")
        private String faceValue; // Номинальная стоимость одной ценной бумаги, в валюте инструмента

        @XmlAttribute(name = "STATUS")
        private String status; // Индикатор &quot;торговые операции разрешены/запрещены&quot;

        @XmlAttribute(name = "BOARDNAME")
        private String boardName; // Режим торгов

        @XmlAttribute(name = "DECIMALS")
        private String decimals; // Точность, знаков после запятой

        @XmlAttribute(name = "SECNAME")
        private String secName; // Наименование финансового инструмента

        @XmlAttribute(name = "REMARKS")
        private String remarks; //

        @XmlAttribute(name = "MARKETCODE")
        private String marketcCode; // Идентификатор рынка на котором торгуется финансовый инструмент

        @XmlAttribute(name = "INSTRID")
        private String instrId; // Группа инструментов

        @XmlAttribute(name = "SECTORID")
        private String sectorId; //

        @XmlAttribute(name = "MINSTEP")
        private String minStep; // Минимально возможная разница между ценами, указанными в заявках на покупку/продажу ценных бумаг

        @XmlAttribute(name = "PREVWAPRICE")
        private String prevWaPrice; // Значение оценки (WAPRICE) предыдущего торгового дня

        @XmlAttribute(name = "FACEUNIT")
        private String faceUnit; // Код валюты, в которой выражен номинал ценной бумаги

        @XmlAttribute(name = "PREVDATE")
        private String prevDate; // Дата предыдущего торгового дня

        @XmlAttribute(name = "ISSUESIZE")
        private String issueSize; // Объем выпуска

        @XmlAttribute(name = "ISIN")
        private String isin; // Международный идентификационный код ценной бумаги

        @XmlAttribute(name = "LATNAME")
        private String latName;

        @XmlAttribute(name = "REGNUMBER")
        private String regNumber; // Номер государственной регистрации

        @XmlAttribute(name = "PREVLEGALCLOSEPRICE")
        private String prevLegalClosePrice; // Цена закрытия предыдущего дня

        @XmlAttribute(name = "PREVADMITTEDQUOTE")
        private String prevAdmittedQuote; // Признаваемая котировка предыдущего дня

        @XmlAttribute(name = "CURRENCYID")
        private String currencyId; // Валюта расчетов

        @XmlAttribute(name = "SECTYPE")
        private String secType; // Тип ценной бумаги

        @XmlAttribute(name = "LISTLEVEL")
        private String listLevel; // Уровень листинга

        @XmlAttribute(name = "SETTLEDATE")
        private String settleDate; // Дата расчетов сделки
}
