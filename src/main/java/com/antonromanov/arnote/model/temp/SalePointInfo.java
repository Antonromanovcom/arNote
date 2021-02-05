package com.antonromanov.arnote.model.temp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalePointInfo {

    private String salePointId;
    private String nameSalePoint;
    private String parentId;
    private Boolean activFlg;
    private Integer staffCount;
    private Integer fot;
    private String comment;
    private AddressInfo addressInfo;
    private String fullAddress;
    private List<ContactRs> listOfContact;
    private TeamInfo teamInfo;

}
