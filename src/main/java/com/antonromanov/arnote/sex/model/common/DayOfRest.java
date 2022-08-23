package com.antonromanov.arnote.model.common;

import lombok.Data;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "day")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class DayOfRest {

    @XmlAttribute(name = "d")
    String date;

    @XmlAttribute(name = "t")
    String type; // t (type) - тип дня: 1 - выходной день, 2 - рабочий и сокращенный (может быть использован для любого дня недели), 3 - рабочий день (суббота/воскресенье)

    @XmlAttribute(name = "h")
    String holiday; // номер праздника (ссылка на атрибут id тэга holiday)
}
