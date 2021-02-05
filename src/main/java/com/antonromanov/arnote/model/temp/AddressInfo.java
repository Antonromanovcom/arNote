package com.antonromanov.arnote.model.temp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressInfo {

    private String addressId;
    private Boolean activFlg;
    private String postalCode;
    private String city;
    private String province;
    private String state;
    private String sbrfLocality;
    private String street;
    private String building;
    private String house;
    private String sbrfHousing;
    private String apartmentNumber;

}
