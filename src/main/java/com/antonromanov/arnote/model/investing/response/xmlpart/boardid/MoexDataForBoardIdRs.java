package com.antonromanov.arnote.model.investing.response.xmlpart.boardid;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;


@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class MoexDataForBoardIdRs {

    @XmlElementWrapper(name = "rows")
    @XmlElement(name = "row")
    ArrayList<MoexRowsForBoardIdRs> rowList = new ArrayList <> (); // todo: упростить до 1 набора классов

}
