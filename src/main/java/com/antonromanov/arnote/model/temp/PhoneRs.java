package com.antonromanov.arnote.model.temp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhoneRs {

    private String phoneId;
    private String phoneType;
    private String phoneNumber;
    private String extNumber;
    private Boolean activFlg;
    private Boolean primaryFlg;

}
