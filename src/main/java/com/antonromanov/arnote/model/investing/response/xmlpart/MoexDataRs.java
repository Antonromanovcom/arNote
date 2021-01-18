package com.antonromanov.arnote.model.investing.response.xmlpart;

import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;


@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MoexDataRs {

    @XmlElementWrapper(name = "rows")
    @XmlElement(name = "row")
    ArrayList<MoexRowsRs2> row = new ArrayList <> ();

}
