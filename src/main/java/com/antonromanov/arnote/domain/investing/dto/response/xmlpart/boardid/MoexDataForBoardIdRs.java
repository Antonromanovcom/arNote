package com.antonromanov.arnote.domain.investing.dto.response.xmlpart.boardid;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;


@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MoexDataForBoardIdRs {

    @XmlElementWrapper(name = "rows")
    @XmlElement(name = "row")
    ArrayList<MoexRowsForBoardIdRs> rowList = new ArrayList <> ();

}
