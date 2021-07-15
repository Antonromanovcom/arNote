package com.antonromanov.arnote.domain.investing.dto.response.xmlpart.currentquote;

import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.common.CommonMoexDoc;
import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "document")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MoexDocumentRs implements CommonMoexDoc {
    @XmlElement(name = "data")
    MoexDataRs data;
}
