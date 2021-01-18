package com.antonromanov.arnote.model.investing.response.xmlpart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "row")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoexRowsRs2 {

        @XmlAttribute(name = "SECID")
        private String secid;

        @XmlAttribute(name = "PREVADMITTEDQUOTE")
        private String prevAdmittedQuote;
}
