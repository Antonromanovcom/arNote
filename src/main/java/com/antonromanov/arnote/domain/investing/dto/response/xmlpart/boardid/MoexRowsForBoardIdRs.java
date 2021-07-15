package com.antonromanov.arnote.domain.investing.dto.response.xmlpart.boardid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "row")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoexRowsForBoardIdRs {

        @XmlAttribute(name = "secid")
        private String secid;

        @XmlAttribute(name = "boardid")
        private String boardId;

        @XmlAttribute(name = "is_primary")
        @XmlJavaTypeAdapter(BooleanAdapter.class)
        private Boolean isPrimary;
}
