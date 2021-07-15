package com.antonromanov.arnote.domain.investing.dto.response.xmlpart.instrumentinfo;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;


@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MoexInstrumentDetailDataRs {
    @XmlAttribute(name = "id")
    private String id;

    @XmlElementWrapper(name = "rows")
    @XmlElement(name = "row")
    ArrayList<MoexInstrumentDetailRowsRs> rowsList = new ArrayList <> ();

}
