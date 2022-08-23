package com.antonromanov.arnote.model.investing.response.xmlpart.instrumentinfo;

import com.antonromanov.arnote.model.investing.response.xmlpart.common.CommonMoexDoc;
import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlRootElement(name = "document")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MoexDetailInfoRs implements CommonMoexDoc {

    @XmlElement(name = "data")
    ArrayList<MoexInstrumentDetailDataRs> dataList = new ArrayList <> ();
}
