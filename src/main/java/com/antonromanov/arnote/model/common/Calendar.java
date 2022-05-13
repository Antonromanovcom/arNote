package com.antonromanov.arnote.model.common;

import lombok.Data;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlRootElement(name = "calendar")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Calendar {

    @XmlAttribute(name = "year")
    String year;

    @XmlAttribute(name = "lang")
    String lang;

    @XmlAttribute(name = "date")
    String date;

    @XmlElementWrapper(name = "holidays")
    @XmlElement(name = "holiday")
    ArrayList<Holiday> holidays = new ArrayList <> ();

    @XmlElementWrapper(name = "days")
    @XmlElement(name = "day")
    ArrayList<DayOfRest> days = new ArrayList <> ();
}
