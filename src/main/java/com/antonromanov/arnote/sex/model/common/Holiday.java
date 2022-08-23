package com.antonromanov.arnote.model.common;

import lombok.Data;
import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Holiday {

    @XmlAttribute(name = "id")
    String year;

    @XmlAttribute(name = "title")
    String title;
}
