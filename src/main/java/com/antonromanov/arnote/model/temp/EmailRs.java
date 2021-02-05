package com.antonromanov.arnote.model.temp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailRs {

    private String emailId;
    private String emailType;
    private String email;
    private Boolean activFlg;
    private Boolean primaryFlg;
}
