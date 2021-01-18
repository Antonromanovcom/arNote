package com.antonromanov.arnote.model.investing.response.xmlpart;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "document")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MoexDocumentRs {
        @XmlElement(name = "data")
        MoexDataRs data;
}
