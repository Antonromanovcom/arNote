package com.antonromanov.arnote.domain.investing.dto.response.xmlpart.currentquote;

import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;


@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MoexDataRs {

    @XmlElementWrapper(name = "rows")
    @XmlElement(name = "row")
    ArrayList<MoexRowsRs> row = new ArrayList <> ();

}
