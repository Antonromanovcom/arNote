package com.antonromanov.arnote.model.temp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactRs {

    private String contactId;
    private Boolean activFlg;
    private String lastName;
    private String firstName;
    private String middleName;
    private String gender;
    private String dateOfBirth;
    private String jobTitle;
    private String comments;
    private Boolean primaryFlg;
    private List<PhoneRs> listOfPhone;
    private List<EmailRs> listOfEmail;

}
